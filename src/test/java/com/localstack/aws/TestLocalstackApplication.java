package com.localstack.aws;

import org.springframework.boot.SpringApplication;

public class TestLocalstackApplication {

    public static void main(String[] args) {
        SpringApplication.from(LocalstackApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
