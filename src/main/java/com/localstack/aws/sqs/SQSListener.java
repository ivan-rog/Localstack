package com.localstack.aws.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SQSListener {

    @SqsListener("${app.queue-name}")
    public void receiveMessage(String message) {
        log.info("Received message: {}", message);
    }
}
