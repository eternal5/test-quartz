package com.test.mapper;

import com.test.pojo.CronTrigger;
import com.test.pojo.CronTriggerKey;

public interface CronTriggerMapper {
    int deleteByPrimaryKey(CronTriggerKey key);

    int insert(CronTrigger record);

    int insertSelective(CronTrigger record);

    CronTrigger selectByPrimaryKey(CronTriggerKey key);

    int updateByPrimaryKeySelective(CronTrigger record);

    int updateByPrimaryKey(CronTrigger record);
}