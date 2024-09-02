package com.ms.notification.controller.api;

import lombok.RequiredArgsConstructor;

import com.ms.notification.client.WebsocketClient;
import com.ms.notification.service.NotificationClientService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import main.java.com.leon.baobui.dto.request.NotificationRequest;
import main.java.com.leon.baobui.dto.response.notification.NotificationResponse;

import static main.java.com.leon.baobui.constants.PathConstants.API_V1_NOTIFICATION;
import static main.java.com.leon.baobui.constants.PathConstants.TWEET;
import static main.java.com.leon.baobui.constants.WebsocketConstants.TOPIC_FEED;
import static main.java.com.leon.baobui.constants.WebsocketConstants.TOPIC_NOTIFICATIONS;
import static main.java.com.leon.baobui.constants.WebsocketConstants.TOPIC_TWEET;

@Controller
@RequestMapping(API_V1_NOTIFICATION)
@RequiredArgsConstructor
public class NotificationApiController {
    private final WebsocketClient webSocketClient;
    private final NotificationClientService notificationClientService;

    @PostMapping(TWEET)
    public ResponseEntity<NotificationResponse> sendTweetNotification(@RequestBody NotificationRequest notificationRequest) {
        NotificationResponse notification = notificationClientService.sendNotification(notificationRequest);
        sendTopicNotification(notification, notification.getTweet().getAuthorId());
        webSocketClient.send(TOPIC_FEED, notification);
        webSocketClient.send(TOPIC_TWEET + notification.getTweet().getId(), notification);
        return ResponseEntity.ok(notification);
    }

    private void sendTopicNotification(NotificationResponse notification, Long notificationTopicId) {
        if (notification.getId() != null) {
            webSocketClient.send(TOPIC_NOTIFICATIONS + notificationTopicId, notification);
        }
    }
}
