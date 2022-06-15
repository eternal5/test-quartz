package com.test.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.dto.JobAndTriggerDto;
import com.test.job.HttpJob;
import com.test.mapper.JobDetailMapper;
import com.test.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private JobDetailMapper jobDetailMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 获取定时任务信息
     * @param pageNum 页数
     * @param pageSize 页大小
     */
    @Override
    public PageInfo<JobAndTriggerDto> getJobAndTriggerDetails(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobAndTriggerDto> list = jobDetailMapper.getJobAndTriggerDetails();
        return new PageInfo<>(list);
    }

    /**
     * 新增定时任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     * @param tName 触发器名称
     * @param tGroup 触发器组
     * @param cron cron表达式
     * @param url 请求地址
     */
    @Override
    public void addjob(String jName, String jGroup, String tName, String tGroup, String cron, String url) {
        try {
            // 构建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(HttpJob.class)
                    .withIdentity(jName, jGroup)
                    .usingJobData("url", url)
                    .build();
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(tName, tGroup)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            // 启动调度器
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.info("创建定时任务失败" + e);
        }
    }

    /**
     * 暂停任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     */
    @Override
    public void pausejob(String jName, String jGroup) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jName, jGroup));
    }

    /**
     * 恢复任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     */
    @Override
    public void resumejob(String jName, String jGroup) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jName, jGroup));
    }

    /**
     * 删除任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     */
    @Override
    public void deletejob(String jName, String jGroup) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jName, jGroup));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jName, jGroup));
        scheduler.deleteJob(JobKey.jobKey(jName, jGroup));
    }
}
