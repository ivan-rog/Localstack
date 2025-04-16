package com.localstack.aws.entitys.sqs;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationType {
    SUCCESS,
    ERROR;

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
