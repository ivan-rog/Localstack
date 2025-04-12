package com.localstack.aws.business;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsService {

    private final SqsTemplate sqsTemplate;

    public void sendMessage(String queueName, String message) {
        sqsTemplate.send(queueName, message);
    }

}
