package com.localstack.aws.entitys.request;

import com.localstack.aws.entitys.sqs.NotificationType;

public record SqsRequest(
        String queueName,
        NotificationType notificationType,
        String message
) {
}
