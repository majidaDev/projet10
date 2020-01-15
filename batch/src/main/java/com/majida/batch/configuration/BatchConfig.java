package com.majida.batch.configuration;

import com.majida.batch.proxies.MicroserviceLoanBatchProxy;
import com.majida.batch.tasklet.LoanTasklet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@ComponentScan("com")
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    private static final Logger LOGGER = LogManager.getLogger(BatchConfig.class);

    @Autowired
    public JobBuilderFactory jobs;

    @Autowired
    public StepBuilderFactory steps;

    @Bean
    public Step stepOne(MicroserviceLoanBatchProxy microserviceLoanBatchProxy, JavaMailSender javaMailSender){
        return steps.get("stepOne")
                .tasklet(new LoanTasklet(microserviceLoanBatchProxy, javaMailSender))
                .build();
    }

    @Bean
    public Job demoJob(MicroserviceLoanBatchProxy microserviceLoanBatchProxy, JavaMailSender javaMailSender){
        return jobs.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(stepOne(microserviceLoanBatchProxy, javaMailSender))
                .build();
    }

}
