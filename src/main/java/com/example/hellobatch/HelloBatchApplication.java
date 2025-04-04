
package com.example.hellobatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class HelloBatchApplication implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job exportUserJob;

    public HelloBatchApplication(JobLauncher jobLauncher, @Qualifier("exportUserJob") Job exportUserJob) {
        this.jobLauncher = jobLauncher;
        this.exportUserJob = exportUserJob;
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloBatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 CLI 실행 인자: " + Arrays.toString(args));

        Map<String, JobParameter> paramMap = new HashMap<>();

        for (String arg : args) {
            String[] pair = arg.split("=");
            if (pair.length == 2) {
                paramMap.put(pair[0], new JobParameter(pair[1]));
            }
        }

        // 중복 실행 방지용 추가 파라미터
        paramMap.put("time", new JobParameter(System.currentTimeMillis()));

        JobParameters jobParameters = new JobParameters(paramMap);

        jobLauncher.run(exportUserJob, jobParameters);
    }
}
