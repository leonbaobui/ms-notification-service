package com.ms.notification.model.projection;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

import main.java.com.leon.baobui.dto.response.notification.NotificationTweetResponse;
import main.java.com.leon.baobui.dto.response.notification.NotificationUserResponse;
import main.java.com.leon.baobui.enums.NotificationType;

public interface NotificationProjection {
    Long getId();
    LocalDateTime getDate();
    NotificationType getNotificationType();
    Long getUserId();
    Long getUserToFollowId();
    Long getTweetId();
//    Long getListId();

    @Value("#{target.userId == null ? null : @notificationServiceHelper.getNotificationUser(target.userId)}")
    NotificationUserResponse getUser();

    @Value("#{target.userToFollowId == null ? null : @notificationServiceHelper.getNotificationUser(target.userToFollowId)}")
    NotificationUserResponse getUserToFollow();

    @Value("#{target.tweetId == null ? null : @notificationServiceHelper.getNotificationTweet(target.tweetId)}")
    NotificationTweetResponse getTweet();

//    @Value("#{target.listId == null ? null : @notificationServiceHelper.getNotificationList(target.listId)}")
//    NotificationListResponse getList();
}
