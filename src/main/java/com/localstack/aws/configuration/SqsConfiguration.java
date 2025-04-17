package com.localstack.aws.configuration;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementResultCallback;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.operations.TemplateAcknowledgementMode;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;
import java.util.Collection;

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

    @Bean("myFactory")
    public SqsMessageListenerContainerFactory<Object> sqsMessageListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory.builder()
                .sqsAsyncClient(sqsAsyncClient)
                .configure(options ->
                        options
                                .acknowledgementMode(AcknowledgementMode.MANUAL)
                                .pollTimeout(Duration.ofSeconds(10))
                                .messageConverter(this.messageConverter())
                ).acknowledgementResultCallback(this.acknowledgementResultCallback())
                .build();
    }

    private AcknowledgementResultCallback<Object> acknowledgementResultCallback() {
        return new AcknowledgementResultCallback<Object>() {
            @Override
            public void onSuccess(Collection<Message<Object>> messages) {
                AcknowledgementResultCallback.super.onSuccess(messages);
            }

            @Override
            public void onFailure(Collection<Message<Object>> messages, Throwable t) {
                AcknowledgementResultCallback.super.onFailure(messages, t);
            }
        };
    }

    private SqsMessagingMessageConverter messageConverter() {
        SqsMessagingMessageConverter converter = new SqsMessagingMessageConverter();
        converter.doNotSendPayloadTypeHeader();
        MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
        jsonConverter.setPrettyPrint(true);
        jsonConverter.setStrictContentTypeMatch(Boolean.FALSE);
        converter.setPayloadMessageConverter(jsonConverter);
        return converter;
    }


}