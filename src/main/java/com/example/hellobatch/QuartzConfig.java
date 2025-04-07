
package com.example.hellobatch;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Configuration
public class QuartzConfig {

    @SuppressWarnings("unchecked")
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob((Class<? extends Job>) HelloJobScheduler.class)
                .withIdentity("helloJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger oneTimeTrigger() {
        // 오늘 날짜 기준, 원하는 시각으로 설정 (예: 오후 3시)
        // LocalDateTime startAt = LocalDate.now().atTime(15, 0); // 15:00
        LocalDateTime startAt = LocalDateTime.now().plusMinutes(1); // 1분 뒤, 테스트용
        Date startDate = Date.from(startAt.atZone(ZoneId.systemDefault()).toInstant());

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("oneTimeTrigger")
                .startAt(startDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(0)) // ✅ 한 번만 실행
                .build();
    }

}
