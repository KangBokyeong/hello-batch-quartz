package com.example.hellobatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

public class JobResultListener extends JobExecutionListenerSupport {

    @Autowired
    private JavaMailSender mailSender;

    public JobResultListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        int readCount = jobExecution.getStepExecutions().stream().mapToInt(StepExecution::getReadCount).sum();
        int writeCount = jobExecution.getStepExecutions().stream().mapToInt(StepExecution::getWriteCount).sum();

        StringBuilder body = new StringBuilder();
        body.append("✅ 배치 작업 완료\n")
                .append("읽은 건수: ").append(readCount).append("\n")
                .append("쓴 건수: ").append(writeCount).append("\n");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("kbk9818@naver.com");
        message.setSubject("[Batch 완료 보고]");
        message.setText(body.toString());

        try {
            mailSender.send(message);
            System.out.println("📧 통계 이메일 전송 완료");
        } catch (Exception e) {
            System.out.println("⚠ 통계 이메일 전송 실패");
            e.printStackTrace();
        }
    }
}
