package com.test.controller;

import com.github.pagehelper.PageInfo;
import com.test.dto.JobAndTriggerDto;
import com.test.service.QuartzService;
import com.test.vo.ResultMap;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(path = "/quartz")
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    /**
     * 新增定时任务
     */
    @PostMapping(path = "/addJob")
    @ResponseBody
    public ResultMap addJob(@RequestBody Map<String, Object> requestParams) {
        String jName = (String) requestParams.get("jName"); // 任务名称
        String jGroup = (String) requestParams.get("jGroup");   // 任务组
        String tName = (String) requestParams.get("tName"); // 触发器名称
        String tGroup = (String) requestParams.get("tGroup");   // 触发器组
        String cron = (String) requestParams.get("cron");   // cron表达式
        String url = (String) requestParams.get("url"); // 请求url
        try {
            quartzService.addjob(jName, jGroup, tName, tGroup, cron, url);
            return new ResultMap().success().message("添加任务成功");
        } catch (Exception e) {
            log.error("添加任务失败", e);
            return new ResultMap().error().message("添加任务失败");
        }
    }

    /**
     * 暂停任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     * @return ResultMap
     */
    @PostMapping(path = "/pauseJob")
    @ResponseBody
    public ResultMap pauseJob(String jName, String jGroup) {
        try {
            quartzService.pausejob(jName, jGroup);
            return new ResultMap().success().message("暂停任务成功");
        } catch (SchedulerException e) {
            log.error("暂停任务失败", e);
            return new ResultMap().error().message("暂停任务失败");
        }
    }

    /**
     * 恢复任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     * @return ResultMap
     */
    @PostMapping(path = "/resumeJob")
    @ResponseBody
    public ResultMap resumeJob(String jName, String jGroup) {
        try {
            quartzService.resumejob(jName, jGroup);
            return new ResultMap().success().message("恢复任务成功");
        } catch (SchedulerException e) {
            log.error("恢复任务失败", e);
            return new ResultMap().error().message("恢复任务失败");
        }
    }

    /**
     * 删除任务
     *
     * @param jName 任务名称
     * @param jGroup 任务组
     * @return ResultMap
     */
    @PostMapping(path = "/deleteJob")
    @ResponseBody
    public ResultMap deleteJob(String jName, String jGroup) {
        try {
            quartzService.deletejob(jName, jGroup);
            return new ResultMap().success().message("删除任务成功");
        } catch (SchedulerException e) {
            log.error("删除任务失败", e);
            return new ResultMap().error().message("删除任务失败");
        }
    }

    /**
     * 查询任务
     *
     * @param pageNum 页码
     * @param pageSize 每页显示多少条数据
     * @return Map
     */
    @GetMapping(path = "/queryJob")
    @ResponseBody
    public ResultMap queryJob(Integer pageNum, Integer pageSize) {
        PageInfo<JobAndTriggerDto> pageInfo = quartzService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(pageInfo.getTotal())) {
            map.put("list", pageInfo);
            map.put("total", pageInfo.getTotal());
            return new ResultMap().success().data(map).message("查询任务成功");
        }
        return new ResultMap().fail().message("查询任务成功失败，没有数据");
    }
}
