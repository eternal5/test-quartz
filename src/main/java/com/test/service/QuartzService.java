package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.dto.JobAndTriggerDto;
import org.quartz.SchedulerException;


public interface QuartzService {

    PageInfo<JobAndTriggerDto> getJobAndTriggerDetails(Integer pageNum, Integer pageSize);

    void addjob(String jName, String jGroup, String tName, String tGroup, String cron, String url);

    void pausejob(String jName, String jGroupe) throws SchedulerException;

    void resumejob(String jName, String jGroup) throws SchedulerException;

    void deletejob(String jName, String jGroup) throws SchedulerException;
}
