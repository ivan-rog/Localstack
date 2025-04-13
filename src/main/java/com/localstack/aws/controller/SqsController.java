package com.localstack.aws.controller;

import com.localstack.aws.business.SqsService;
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
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        return ResponseEntity.ok("Message sent: " + sqsService.sendMessage("localstack-queue", message));
    }
}
