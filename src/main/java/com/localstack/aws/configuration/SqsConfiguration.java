package com.localstack.aws.configuration;

import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.operations.TemplateAcknowledgementMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;

@Configuration
public class SqsConfiguration {

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsAsyncClient)
                .configure(sqsTemplateOptions ->
                        sqsTemplateOptions.acknowledgementMode(TemplateAcknowledgementMode.MANUAL)
                                .queueNotFoundStrategy(QueueNotFoundStrategy.FAIL)
                                .defaultMaxNumberOfMessages(10)
                                .defaultPollTimeout(Duration.ofSeconds(10))
                )
                .build();
    }

}