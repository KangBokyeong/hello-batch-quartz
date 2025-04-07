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

    public void execute(JobExecutionContext quartzContext) {
        System.out.println("🕐 Quartz 트리거 실행됨!");
        try {
            JobParameters params = new JobParametersBuilder()
                    .addString("joinedAfter", "2025-04-01")
                    .addString("outputFile", "/Users/b.k.kang/Desktop/users.csv")
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            // ✅ CSV 저장용 Job
            org.springframework.batch.core.Job exportUserJob =
                    (org.springframework.batch.core.Job) context.getBean("exportUserJob");

            // 먼저 CSV 저장
            jobLauncher.run(exportUserJob, params);

            // ✅ 모든 작업이 끝난 후 애플리케이션 종료
            //System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
