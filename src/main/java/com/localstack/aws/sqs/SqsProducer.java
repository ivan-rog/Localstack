package com.localstack.aws.sqs;

import com.localstack.aws.entitys.request.SqsRequest;
import com.localstack.aws.entitys.sqs.NotificationSQS;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SqsProducer {

    private final SqsTemplate sqsTemplate;

    public String sendMessage(SqsRequest sqsRequest) {
        var message = new NotificationSQS();
        message.setIdNotification(UUID.randomUUID().toString());
        message.setNotificationMessage(sqsRequest.message());
        message.setNotificationType(sqsRequest.notificationType());
        return sqsTemplate.send(sqsRequest.queueName(),
                MessageBuilder
                        .withPayload(message)
                        .build()).messageId().toString();
    }

}
