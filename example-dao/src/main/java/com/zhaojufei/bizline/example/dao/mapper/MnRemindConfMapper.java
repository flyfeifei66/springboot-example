package com.zhaojufei.bizline.example.dao.mapper;

import java.util.List;

import com.zhaojufei.bizline.example.dao.entity.MnRemindConf;
import com.zhaojufei.bizline.example.dao.entity.PageMnRemindConfDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件提醒配置表(MnRemindConf)表数据库访问层
 *
 * @author ZhaoJuFei
 * @since 2020-05-19 09:16:14
 */
@Mapper
public interface MnRemindConfMapper {

	/**
	 * 查询指定行数据
	 *
	 * @param pageMnRemindConfDto 查询条件
	 * @return 对象列表
	 */
	List<MnRemindConf> queryAllByLimit(PageMnRemindConfDto pageMnRemindConfDto);

}