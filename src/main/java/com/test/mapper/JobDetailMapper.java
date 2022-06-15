package com.test.mapper;

import com.test.dto.JobAndTriggerDto;
import com.test.pojo.JobDetail;
import com.test.pojo.JobDetailKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobDetailMapper {
    int deleteByPrimaryKey(JobDetailKey key);

    int insert(JobDetail record);

    int insertSelective(JobDetail record);

    JobDetail selectByPrimaryKey(JobDetailKey key);

    int updateByPrimaryKeySelective(JobDetail record);

    int updateByPrimaryKeyWithBLOBs(JobDetail record);

    int updateByPrimaryKey(JobDetail record);

    List<JobAndTriggerDto> getJobAndTriggerDetails();
}