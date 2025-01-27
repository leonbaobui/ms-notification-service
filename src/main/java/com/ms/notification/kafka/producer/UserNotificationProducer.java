package com.ms.notification.kafka.producer;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import main.java.com.leon.baobui.constants.KafkaTopicConstants;

@Component
@RequiredArgsConstructor
public class UserNotificationProducer {
    private final KafkaTemplate<String, Long> notificationProducer;

    public void resetNotificationCount(Long userId) {
        notificationProducer.send(KafkaTopicConstants.RESET_USER_NOTIFICATIONS_COUNT_TOPIC, userId);
    }
}
