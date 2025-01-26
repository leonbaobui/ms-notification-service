package com.ms.notification.controller.rest;

import java.util.List;

import lombok.RequiredArgsConstructor;
import com.ms.notification.service.NotificationService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.java.com.leon.baobui.constants.PathConstants;
import main.java.com.leon.baobui.dto.HeaderResponse;
import main.java.com.leon.baobui.dto.response.notification.NotificationResponse;

import static main.java.com.leon.baobui.constants.PathConstants.UI_V1_NOTIFICATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UI_V1_NOTIFICATION)
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(PathConstants.USER)
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<NotificationResponse> response = notificationService.getUserNotifications(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

}
