package com.zhaojufei.bizline.example.core.formrepeat.advice;


import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.zhaojufei.bizline.example.core.constant.AdviceOrder;
import com.zhaojufei.bizline.example.core.constant.CommonConstant;
import com.zhaojufei.bizline.example.core.constant.RedisKeyUtil;
import com.zhaojufei.bizline.example.core.distributlock.DistributedLockCallback;
import com.zhaojufei.bizline.example.core.distributlock.DistributedLockTemplate;
import com.zhaojufei.bizline.example.core.domain.UserDTO;
import com.zhaojufei.bizline.example.core.exception.BizException;
import com.zhaojufei.bizline.example.core.formrepeat.annotation.UnRepeatSubmitParam;
import com.zhaojufei.bizline.example.core.formrepeat.annotation.UnRepeatSubmitTokenField;
import com.zhaojufei.bizline.example.core.formrepeat.constant.FormRepeatSubmitConstant;
import com.zhaojufei.bizline.example.core.formrepeat.dto.Token;
import com.zhaojufei.bizline.example.core.util.DmAssert;
import com.zhaojufei.bizline.example.facade.domain.dto.ResponseData;
import com.zhaojufei.bizline.example.facade.domain.enums.ResCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import lombok.extern.slf4j.Slf4j;

/**
 * 基于注解的防重复提交切面类，由于防重复提交是针对的每一个用户，因此需要用户信息。
 * 如果用户信息也是基于注解注入的，需要控制切面的执行顺序，确保本切面在用户切面信息之后。
 *
 * @author zhaojufei
 */
@Aspect
@Component
@Slf4j
@Order(AdviceOrder.UN_REPEAT_SUBMIT)
public class FormRepeatSubmitAop {
	@Autowired
	private DistributedLockTemplate distributedLockTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	@Around("@annotation(com.zhaojufei.bizline.example.core.formrepeat.annotation.UnRepeatSubmit)")
	public Object around(ProceedingJoinPoint point) {
		try {
			return execute(point);
		} catch (BizException e) {
			// 这里很重要，正常报错要抛出去
			throw e;
		} catch (Throwable e) {
			log.error("防重复提交出现异常, errMsg = {}, error stack = {}", e.getMessage(), e);
			throw new BizException(ResCodeEnum.FORM_SUBMIT_FAIL);
		}
	}

	/**
	 * 判断是否是重复提交方法
	 * <p>
	 * 消耗Token时分布式锁锁定精确到token；正常情况下一个用户获取一个使用一个。
	 * 如果一个账号多人同时使用，每人获取token不一样，也可以正常通过。
	 * 同一个token被一个人连续使用多次、或者被多人使用，都只允许第一个通过。
	 */
	public Object execute(ProceedingJoinPoint point) throws Throwable {
		// 获取参数
		UnRepeatParaHandler parameterHandler = new UnRepeatParaHandler(point).resolveParams();
		return distributedLockTemplate.tryLock(new DistributedLockCallback() {
			@Override
			public Object process() {
				// 构造map的key
				String key = RedisKeyUtil.getUserFormOpsTokenMapLockKey(parameterHandler.getTenantId(), parameterHandler.getUserId());

				try {
					// 判断用户的token是否合法
					if (!redisTemplate.opsForHash().hasKey(key, parameterHandler.getToken())) {
						throw new BizException(ResCodeEnum.FORM_SUBMIT_TOKEN_INVALID);
					}

					Object result = point.proceed(point.getArgs());
					// 如果返回类型为非标准信息类型直接返回，无法重复使用token
					if (!(result instanceof ResponseData)) {
						redisTemplate.opsForHash().delete(key, parameterHandler.getToken());
						return result;
					}
					// 如果是MessageBody类型， 且操作成功，删除token
					if (result != null && ((ResponseData) result).isSuccess()) {
						redisTemplate.opsForHash().delete(key, parameterHandler.getToken());
						// 如果操作不成功，放回token，供下次使用
					} else {
						Token token = new Token(parameterHandler.getToken(), new Date());
						redisTemplate.opsForHash().put(key, parameterHandler.getToken(), token);
						redisTemplate.expire(key, FormRepeatSubmitConstant.TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
					}

					return result;

				} catch (BizException e) {
					throw e;
				} catch (Throwable e) {
					log.error("防重复提交捕捉到异常, errMsg = {}, error stack = {}", e.getMessage(), e);
					throw new BizException(ResCodeEnum.FORM_SUBMIT_FAIL);
				}
			}

			/**
			 * 得到分布式锁Key
			 */
			@Override
			public String getLockName() {
				return RedisKeyUtil.getUserFormOpsLockKey(parameterHandler.getTenantId(),
						parameterHandler.getUserId() + "_" + parameterHandler.getToken());
			}
		}, true);
	}

	/**
	 * 参数解析内部类
	 */
	private class UnRepeatParaHandler {
		private ProceedingJoinPoint point;
		private String userId;
		private String tenantId;
		private String token;

		public UnRepeatParaHandler(ProceedingJoinPoint point) {
			this.point = point;
		}

		public UnRepeatParaHandler resolveParams() throws IllegalAccessException {
			// 1、获取到所有的参数值的数组
			Object[] args = point.getArgs();

			// 2、获取参数
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			UserDTO userDTO = (UserDTO) request.getAttribute(CommonConstant.USER);
			if (Objects.nonNull(userDTO)) {
				this.tenantId = userDTO.getTenantId();
				this.userId = userDTO.getId();
			}

			MethodSignature signature = (MethodSignature) point.getSignature();
			Parameter[] parameters = signature.getMethod().getParameters();

			log.info("参数列表：{}", parameters);
			log.info("值列表{}", args);

			// 3、寻找本aop需要的参数
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				// 只有标有VCParam注解的类，才可以执行
				if (parameter.getAnnotation(UnRepeatSubmitParam.class) == null) {
					continue;
				}
				// 获取类型所对应的参数对象
				Object arg = args[i];
				// 获取当前参数类型
				Class<?> paramClazzTmp = parameter.getType();
				// 得到参数的所有成员变量
				List<Field> fieldList = new ArrayList<>();

				// 遍历paramClazzTmp及其所有父类，并收集所有field
				while (paramClazzTmp != null) {
					fieldList.addAll(Arrays.asList(paramClazzTmp.getDeclaredFields()));
					paramClazzTmp = paramClazzTmp.getSuperclass();
				}
				for (Field field : fieldList) {
					field.setAccessible(true);
					UnRepeatSubmitTokenField unRepeatTokenField = field.getAnnotation(UnRepeatSubmitTokenField.class);
					if (unRepeatTokenField != null) {
						token = (String) field.get(arg);
					}
				}

				// 所有参数已经找到，不再继续找
				if (StringUtils.isNotBlank(token)) {
					break;
				}
			}

			DmAssert.notBlank(tenantId, ResCodeEnum.USER_TENANT_ID_EMPTY);
			DmAssert.notBlank(userId, ResCodeEnum.USER_ID_EMPTY);
			DmAssert.notBlank(token, ResCodeEnum.USER_TOKEN_EMPTY);

			return this;
		}

		public String getUserId() {
			return userId;
		}

		public String getTenantId() {
			return tenantId;
		}

		public String getToken() {
			return token;
		}
	}
}
