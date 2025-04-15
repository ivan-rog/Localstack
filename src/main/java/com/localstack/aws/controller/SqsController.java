package com.localstack.aws.controller;

import com.localstack.aws.business.SqsService;
import com.localstack.aws.entitys.request.SqsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/v1/sqs")
@RequiredArgsConstructor
public class SqsController {

    private final SqsService sqsService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody SqsRequest sqsRequest) {
        return ResponseEntity.ok("Message sent: " + sqsService.sendMessage(sqsRequest.queueName(), sqsRequest.message()));
    }
}
