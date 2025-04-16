package com.localstack.aws.entitys.sqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationSQS {
        private String idNotification;
        private NotificationType notificationType;
        private String notificationMessage;

        @Override
        public String toString() {
                ObjectMapper mapper = new ObjectMapper();
                try {
                        return mapper.writeValueAsString(this);
                } catch (Exception e) {
                        return "";
                }
        }
}
