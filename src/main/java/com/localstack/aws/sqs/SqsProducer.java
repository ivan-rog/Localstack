package com.localstack.aws.sqs;

import com.localstack.aws.entitys.request.SqsRequest;
import com.localstack.aws.entitys.sqs.NotificationSQS;
import io.awspring.cloud.sqs.operations.MessagingOperationFailedException;
import io.awspring.cloud.sqs.operations.SendBatchOperationFailedException;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SqsProducer {

    private final SqsTemplate sqsTemplate;

    public String sendMessage(SqsRequest sqsRequest) {
        var message = new NotificationSQS();
        message.setIdNotification(UUID.randomUUID().toString());
        message.setNotificationMessage(sqsRequest.message());
        message.setNotificationType(sqsRequest.notificationType());
        try {
            var result = sqsTemplate.send(sqsRequest.queueName(),
                    MessageBuilder
                            .withPayload(message)
                            .build());
            return result.messageId().toString();
        } catch (MessagingOperationFailedException ex) {
            log.error("Error send queue message: {}", ex.getMessage(), ex);
            return "";
        }
    }

    private void sendMessageBatch() {
        try {
            sqsTemplate.sendManyAsync("localstack-queue",
                    List.of(
                            MessageBuilder.withPayload(new NotificationSQS()).build(),
                            MessageBuilder.withPayload(new NotificationSQS()).build()));
        } catch (SendBatchOperationFailedException ex) {
            ex.getSendBatchResult().failed().forEach(failed -> log.error("Error send batch message: {}", failed.errorMessage()));
            log.error("Error send batch message: {}", ex.getMessage(), ex);
        }

    }

}
