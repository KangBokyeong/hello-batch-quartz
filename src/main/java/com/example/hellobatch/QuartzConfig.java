
package com.example.hellobatch;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Trigger trigger() {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("helloTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();
    }
}
