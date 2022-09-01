package com.zhaojufei.bizline.example.core.distributlock;


import com.zhaojufei.bizline.example.core.exception.BizException;
import com.zhaojufei.bizline.example.facade.domain.enums.ResCodeEnum;


/**
 * @author zhaojufei
 */
public interface DistributedLockCallback<T> {

    /**
     * 调用者必须在此方法中实现需要加分布式锁的业务逻辑
     */
    T process();

    /**
     * 默认加锁失败时执行方法。
     */
    default T fail() {
        throw new BizException(ResCodeEnum.NOT_GET_LOCK);
    }

    /**
     * 得到分布式锁Key
     */
    String getLockName();

}
