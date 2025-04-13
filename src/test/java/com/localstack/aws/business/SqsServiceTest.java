package com.localstack.aws.business;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@SpringBootTest
@Testcontainers
@Slf4j
class SqsServiceTest {

    @Container
    static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
            .withServices(LocalStackContainer.Service.SQS);

    static final String QUEUE_NAME = "test-queue";

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.queue-name", () -> QUEUE_NAME);
        registry.add(
                "spring.cloud.aws.region.static",
                () -> localStack.getRegion()
        );
        registry.add(
                "spring.cloud.aws.credentials.access-key",
                () -> localStack.getAccessKey()
        );
        registry.add(
                "spring.cloud.aws.credentials.secret-key",
                () -> localStack.getSecretKey()
        );
        registry.add(
                "spring.cloud.aws.sqs.endpoint",
                () -> localStack.getEndpointOverride(SQS).toString()
        );
    }

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        localStack.execInContainer(
                "awslocal",
                "sqs",
                "create-queue",
                "--queue-name",
                QUEUE_NAME
        );
    }

    @Autowired
    private SqsService sqsService;

    @Test
    void testSendMessage() throws IOException {
        await()
                .pollInterval(Duration.ofSeconds(2))
                .atMost(Duration.ofSeconds(10))
                .ignoreExceptions()
                .untilAsserted(
                        () -> {
                            var id = sqsService.sendMessage(QUEUE_NAME, "Hello World");
                            assertNotNull(id);
                        }
                );
    }

}