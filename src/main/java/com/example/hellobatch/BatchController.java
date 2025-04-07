package com.example.hellobatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.batch.core.Job;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job exportUserJob;

    public BatchController(JobLauncher jobLauncher, @Qualifier("exportUserJob") Job exportUserJob) {
        this.jobLauncher = jobLauncher;
        this.exportUserJob = exportUserJob;
    }

    @PostMapping("/run")
    public String runBatch(
            @RequestParam String joinedAfter,
            @RequestParam String outputFile
    ) throws Exception {
        Map<String, JobParameter> paramMap = new HashMap<>();
        paramMap.put("joinedAfter", new JobParameter(joinedAfter));
        paramMap.put("outputFile", new JobParameter(outputFile));
        paramMap.put("time", new JobParameter(System.currentTimeMillis()));

        JobParameters parameters = new JobParameters(paramMap);
        JobExecution execution = jobLauncher.run(exportUserJob, parameters);

        return "📦 Batch 실행 완료: " + execution.getStatus();
    }

    @PostMapping("/shutdown")
    public String shutdown() {
        System.out.println("🛑 애플리케이션 종료 요청됨!");
        Thread shutdownThread = new Thread(() -> {
            try {
                Thread.sleep(1000); // 응답이 먼저 반환되도록 약간 지연
                System.exit(0);
            } catch (InterruptedException ignored) {}
        });
        shutdownThread.setDaemon(false);
        shutdownThread.start();

        return "🛑 애플리케이션 종료 중...";
    }
}
