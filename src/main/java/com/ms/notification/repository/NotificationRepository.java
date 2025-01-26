package com.ms.notification.repository;

import com.ms.notification.model.Notification;
import com.ms.notification.model.projection.NotificationProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import main.java.com.leon.baobui.enums.NotificationType;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT CASE WHEN count(notification) > 0 THEN true ELSE false END " +
            "FROM Notification notification " +
            "WHERE notification.userId = :userId " +
            "AND notification.tweetId = :tweetId " +
            "AND notification.userId = :authUserId " +
            "AND notification.notificationType = :notificationType")
    boolean isTweetNotificationExists(@Param("userId") Long userId,
                                      @Param("tweetId") Long tweetId,
                                      @Param("authUserId") Long authUserId,
                                      @Param("notificationType") NotificationType type);

    @Query("SELECT notification FROM Notification notification " +
            "WHERE notification.notifiedUserId = :userId " +
            "AND notification.notificationType = 'MENTION' " +
            "ORDER BY notification.date DESC")
    Page<NotificationProjection> getNotificationsByUserId(@Param("userId") Long userId, Pageable pageable);
}
