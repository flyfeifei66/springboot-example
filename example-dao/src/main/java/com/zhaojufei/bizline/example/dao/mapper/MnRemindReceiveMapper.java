package com.zhaojufei.bizline.example.dao.mapper;

import java.util.List;

import com.zhaojufei.bizline.example.dao.entity.MnRemindReceive;
import org.apache.ibatis.annotations.Mapper;


/**
 * 邮件提醒接收邮件地址表(MnRemindReceive)表数据库访问层
 *
 * @author ZhaoJuFei
 * @since 2020-05-19 15:21:17
 */
@Mapper
public interface MnRemindReceiveMapper {

    /**
     * 根据配置id查询所有接收者
     *
     * @param confId 查询起始位置
     * @return 对象列表
     */
    List<MnRemindReceive> queryByConfId(String confId);

}