package com.test.mapper;

import com.test.pojo.Trigger;
import com.test.pojo.TriggerKey;

public interface TriggerMapper {
    int deleteByPrimaryKey(TriggerKey key);

    int insert(Trigger record);

    int insertSelective(Trigger record);

    Trigger selectByPrimaryKey(TriggerKey key);

    int updateByPrimaryKeySelective(Trigger record);

    int updateByPrimaryKeyWithBLOBs(Trigger record);

    int updateByPrimaryKey(Trigger record);
}