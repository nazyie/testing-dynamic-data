package com.example.demo.config;

import com.example.demo.report.service.ReportGenerationService;
import com.example.demo.seeder.UserDataSeederService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DemoBatchConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserDataSeederService userDataSeederService;

//    @Value("${reportName}")
    private String reportName = "testing-report-data";

    @Autowired
    private ReportGenerationService reportGenerationService;

    @Bean
    public Job jobToDemo() {
        return new JobBuilder("jobToDemo", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepToDemo())
                .build();
    }

    @Bean
    public Step stepToDemo() {
        return new StepBuilder("stepToDemo", jobRepository)
            .tasklet(tasklet(), transactionManager)
            .build();
    }

    @Bean
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            userDataSeederService.Seed();

            reportGenerationService.generateReport(reportName);

            return RepeatStatus.FINISHED;
        };
    }

}
