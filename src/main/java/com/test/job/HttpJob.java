package com.test.job;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Http任务封装
 */
@Slf4j
@Component
public class HttpJob implements Job {

    @Autowired
    private OkHttpClient okHttpClient;

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String url = String.valueOf(jobDataMap.get("url")); // 获取请求URL
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().build();

        String result = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (Objects.nonNull(response)) {
                result = response.body().string();
            }
        } catch (Exception e) {
            log.error("http调用出错",e);
        }
        log.info("url:{} | resp: {}", url, result);
    }
}
