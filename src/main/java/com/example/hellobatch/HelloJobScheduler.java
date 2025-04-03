package com.example.hellobatch;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class HelloJobScheduler implements Job {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ApplicationContext context;

    @Override
    public void execute(JobExecutionContext quartzContext) {
        System.out.println("🕐 Quartz 트리거 실행됨!");
        try {
            JobParameters params = new JobParametersBuilder()
                    .addString("joinedAfter", "2025-04-02")
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            // ✅ Spring Batch Job을 전체 경로로 지정!
            org.springframework.batch.core.Job helloJob =
                    (org.springframework.batch.core.Job) context.getBean("helloJob");

            jobLauncher.run(helloJob, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
