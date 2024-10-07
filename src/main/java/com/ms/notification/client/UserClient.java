package com.ms.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import main.java.com.leon.baobui.configuration.FeignConfiguration;
import main.java.com.leon.baobui.dto.response.chat.ChatUserParticipantResponse;
import main.java.com.leon.baobui.dto.response.notification.NotificationUserResponse;
import main.java.com.leon.baobui.dto.response.user.UserResponse;

import static main.java.com.leon.baobui.constants.FeignConstants.USER_SERVICE;
import static main.java.com.leon.baobui.constants.PathConstants.API_V1_USER;
import static main.java.com.leon.baobui.constants.PathConstants.CHAT_PARTICIPANT_USER_ID;
import static main.java.com.leon.baobui.constants.PathConstants.IS_MY_PROFILE_BLOCKED_USER_ID;
import static main.java.com.leon.baobui.constants.PathConstants.IS_USER_BLOCKED_USER_ID;
import static main.java.com.leon.baobui.constants.PathConstants.NOTIFICATION_USER_ID;
import static main.java.com.leon.baobui.constants.PathConstants.NOTIFICATION_USER_USER_ID;
import static main.java.com.leon.baobui.constants.PathConstants.USER_ID;

@FeignClient(name = USER_SERVICE, url = "${service.downstream-url.ms-user-service}", path ="/" + USER_SERVICE + API_V1_USER, contextId = "UserClient", configuration = FeignConfiguration.class)
public interface UserClient {
    @GetMapping(NOTIFICATION_USER_ID)
    void increaseNotificationsCount(@PathVariable("userId") Long userId);

    @GetMapping(NOTIFICATION_USER_USER_ID)
    NotificationUserResponse getNotificationUser(@PathVariable("userId") Long userId);
}