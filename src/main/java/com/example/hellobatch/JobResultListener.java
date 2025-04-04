package com.example.hellobatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobResultListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // 시작 전 로그 (선택)
        System.out.println("🚀 Job 시작합니다...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        int readCount = jobExecution.getStepExecutions()
                .stream().mapToInt(se -> se.getReadCount()).sum();
        int writeCount = jobExecution.getStepExecutions()
                .stream().mapToInt(se -> se.getWriteCount()).sum();
        int skipCount = jobExecution.getStepExecutions()
                .stream().mapToInt(se -> se.getProcessSkipCount()).sum();

        System.out.println("🟢 총 읽은 건수: " + readCount);
        System.out.println("🟡 총 처리 건수: " + (readCount - skipCount));
        System.out.println("✅ 총 저장 건수: " + writeCount);
    }
}
