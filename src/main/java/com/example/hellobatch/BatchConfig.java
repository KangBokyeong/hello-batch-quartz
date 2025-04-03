package com.example.hellobatch;

import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public Step helloStep(StepBuilderFactory stepBuilderFactory,
                          JpaPagingItemReader<User> userReader,
                          JpaItemWriter<User> userWriter) {

        ItemProcessor<User, User> processor = user -> {
            System.out.println("✅ 처리 중: " + user.getName());
            user.setProcessed(true);
            return user;
        };

        return stepBuilderFactory.get("helloStep")
                .<User, User>chunk(10)
                .reader(userReader)
                .processor(processor)
                .writer(userWriter)
                .build();
    }



    @Bean
    @StepScope
    public JpaPagingItemReader<User> userReader(
            EntityManagerFactory emf,
            @Value("#{jobParameters['joinedAfter']}") String joinedAfterStr
    ) {
        LocalDate joinedAfter = LocalDate.parse(joinedAfterStr);

        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setQueryString("SELECT u FROM User u WHERE u.processed = false AND u.joinedAt >= :joinedAt");
        reader.setEntityManagerFactory(emf);
        reader.setParameterValues(Map.of("joinedAt", joinedAfter));
        reader.setPageSize(10);
        reader.setName("userReader");

        return reader;
    }



    @Bean
    public JpaItemWriter<User> userWriter(EntityManagerFactory emf) {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }



}
