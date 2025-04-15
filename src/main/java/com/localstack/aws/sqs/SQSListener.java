package com.localstack.aws.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SQSListener {

    @SqsListener(queueNames = {"${queue.localstack.name}", "${queue.prueba.name}"})
    public void receiveMessage(Message<String> message, Acknowledgement ack) {
        log.info("Received message: {} header {}", message.getPayload(), message.getHeaders());
    }
}
