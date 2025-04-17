package com.localstack.aws.sqs;

import com.localstack.aws.entitys.sqs.NotificationSQS;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;

import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SqsConsumer {

    @SqsListener(queueNames = {"${queue.localstack.name}", "${queue.prueba.name}"}, factory = "myFactory")
    public void receiveMessage(Message<NotificationSQS> message, Acknowledgement acknowledgement) {
        log.info("Received message: {}", message.getPayload());
        acknowledgement.acknowledge();
    }
}
