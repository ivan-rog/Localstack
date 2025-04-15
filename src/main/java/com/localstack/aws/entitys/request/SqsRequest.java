package com.localstack.aws.entitys.request;

public record SqsRequest(
        String queueName,
        String message
) {
}
