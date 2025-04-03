package com.example.hellobatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job helloJob(JobBuilderFactory jobBuilderFactory, Step helloStep) {
        return jobBuilderFactory.get("helloJob")
                .start(helloStep)
                .build();
    }

    @Bean
    public Step helloStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("helloStep")
                .<String, String>chunk(1)
                .reader(new IteratorItemReader<>(List.of("Hello, Batch!")))
                .writer(items -> System.out.println(items.get(0)))
                .build();
    }
}
