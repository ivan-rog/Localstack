package com.localstack.aws.business;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsService {

    private final SqsTemplate sqsTemplate;

    public String sendMessage(String queueName, String message) {
        return sqsTemplate.send(queueName, message).messageId().toString();
    }

}
