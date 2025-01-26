package com.ms.notification.service;

import lombok.RequiredArgsConstructor;
import com.ms.notification.client.TweetClient;
import com.ms.notification.client.UserClient;
import com.ms.notification.kafka.producer.UserNotificationProducer;
import com.ms.notification.model.Notification;
import com.ms.notification.model.projection.NotificationProjection;
import com.ms.notification.repository.NotificationRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.leon.baobui.dto.HeaderResponse;
import main.java.com.leon.baobui.dto.request.NotificationRequest;
import main.java.com.leon.baobui.dto.response.notification.NotificationResponse;
import main.java.com.leon.baobui.dto.response.notification.NotificationTweetResponse;
import main.java.com.leon.baobui.dto.response.notification.NotificationUserResponse;
import main.java.com.leon.baobui.mapper.BasicMapper;
import main.java.com.leon.baobui.util.AuthUtil;


@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserNotificationProducer userNotificationProducer;
    private final BasicMapper basicMapper;
    private final UserClient userClient;
    private final TweetClient tweetClient;
    @Transactional
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        boolean notificationCondition = notificationRequest.isNotificationCondition();

        Notification notification = basicMapper.convertToResponse(notificationRequest, Notification.class);
        if (!notificationRequest.getNotifiedUserId().equals(authUserId)) {
            boolean isNotificationExists = switch (notificationRequest.getNotificationType()) {
                default -> notificationRepository.isTweetNotificationExists(
                        notificationRequest.getNotifiedUserId(),
                        notificationRequest.getTweetId(),
                        authUserId,
                        notificationRequest.getNotificationType());
            };
            if (!isNotificationExists) {
                notificationRepository.save(notification);
                userClient.increaseNotificationsCount(notification.getNotifiedUserId());
                return convertToNotificationResponse(notification, notificationCondition);
            }
        }
        return convertToNotificationResponse(notification, notificationCondition);
    }

    public HeaderResponse<NotificationResponse> getUserNotifications(Pageable pageable) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        userNotificationProducer.resetNotificationCount(authUserId);
        Page<NotificationProjection> notifications = notificationRepository.getNotificationsByUserId(authUserId, pageable);
        return basicMapper.getHeaderResponse(notifications, NotificationResponse.class);
    }

    private NotificationResponse convertToNotificationResponse(Notification notification, boolean notificationCondition) {
        return switch (notification.getNotificationType()) {
            default -> convertToNotificationTweetResponse(notification, notificationCondition);
        };
    }
    private NotificationResponse convertToNotificationTweetResponse(Notification notification, boolean isTweetLiked) {
        NotificationUserResponse userResponse = userClient.getNotificationUser(notification.getUserId());
        NotificationTweetResponse tweetResponse = tweetClient.getNotificationTweet(notification.getTweetId());
        tweetResponse.setNotificationCondition(isTweetLiked);
        NotificationResponse notificationResponse = basicMapper.convertToResponse(notification, NotificationResponse.class);
        notificationResponse.setUser(userResponse);
        notificationResponse.setTweet(tweetResponse);
        return notificationResponse;
    }
}

