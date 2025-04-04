package com.example.hellobatch;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;

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

    // csv 저장
    @Bean
    @StepScope
    public FlatFileItemWriter<User> csvWriter(
            @Value("#{jobParameters['outputFile']}") String outputFilePath
    ) {
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();
        System.out.println("📁 CSV 저장 위치: " + outputFilePath);

        writer.setResource(new FileSystemResource(outputFilePath));
        writer.setHeaderCallback(writer1 -> writer1.write("id,name,email,joinedAt"));
        writer.setLineAggregator(user -> String.format(
                "%d,%s,%s,%s",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getJoinedAt()
        ));
        writer.setAppendAllowed(false); // 덮어쓰기

        return writer;
    }

    @Bean
    public Step exportToCsvStep(StepBuilderFactory stepBuilderFactory,
                                JpaPagingItemReader<User> userReader,
                                FlatFileItemWriter<User> csvWriter) {

        return stepBuilderFactory.get("exportToCsvStep")
                .<User, User>chunk(10)
                .reader(userReader)
                .writer(csvWriter)
                .build();
    }

    @Bean(name = "exportUserJob")
    public Job exportUserJobBean(JobBuilderFactory jobBuilderFactory,
                             Step exportToCsvStep,
                             Step checkDataStep) {
        return jobBuilderFactory.get("exportUserJob")
                .start(checkDataStep)
                .on("NOOP").end() // 처리할 유지 없으면 종료
                .from(checkDataStep).on("*").to(exportToCsvStep).end() // 그 외엔 다음 스탭 진행
                .build();
    }

    @Bean
    public Step checkDataStep(StepBuilderFactory stepBuilderFactory, EntityManagerFactory emf) {
        return stepBuilderFactory.get("checkDataStep")
                .tasklet((contribution, chunkContext) -> {
                    EntityManager entityManager = emf.createEntityManager();
                    List<User> users = entityManager
                            .createQuery("SELECT u FROM User u WHERE u.processed = false AND u.joinedAt >= :date", User.class)
                            .setParameter("date", LocalDate.parse("2025-04-01"))
                            .setMaxResults(1)
                            .getResultList();

                    entityManager.close();

                    if (users.isEmpty()) {
                        System.out.println("❌ 처리할 유저가 없습니다. Job 종료.");
                        contribution.setExitStatus(new ExitStatus("NOOP"));
                        return RepeatStatus.FINISHED;
                    } else {
                        System.out.println("✅ 처리할 유저가 존재합니다.");
                    }

                    return RepeatStatus.FINISHED;
                })
                .build();
    }



}
