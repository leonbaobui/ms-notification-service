package com.ms.notification.helper;

import lombok.RequiredArgsConstructor;
import com.ms.notification.client.TweetClient;
import com.ms.notification.client.UserClient;

import org.springframework.stereotype.Component;

import main.java.com.leon.baobui.dto.response.notification.NotificationTweetResponse;
import main.java.com.leon.baobui.dto.response.notification.NotificationUserResponse;

@Component
@RequiredArgsConstructor
public class NotificationServiceHelper {
    private final UserClient userClient;
    private final TweetClient tweetClient;
    public NotificationUserResponse getNotificationUser(Long userId) {
        return userClient.getNotificationUser(userId);
    }

    public NotificationTweetResponse getNotificationTweet(Long userId) {
        return tweetClient.getNotificationTweet(userId);
    }
}
