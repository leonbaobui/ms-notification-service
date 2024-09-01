package com.ms.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import main.java.com.leon.baobui.configuration.FeignConfiguration;
import main.java.com.leon.baobui.dto.response.chat.ChatTweetResponse;
import main.java.com.leon.baobui.dto.response.notification.NotificationTweetResponse;

import static main.java.com.leon.baobui.constants.FeignConstants.TWEET_SERVICE;
import static main.java.com.leon.baobui.constants.PathConstants.API_V1_TWEETS;
import static main.java.com.leon.baobui.constants.PathConstants.CHAT_TWEET_ID;
import static main.java.com.leon.baobui.constants.PathConstants.NOTIFICATION_TWEET_ID;

@CircuitBreaker(name = TWEET_SERVICE)
@FeignClient(name = TWEET_SERVICE, url = "${service.downstream-url.ms-tweet-service}", path ="/" + TWEET_SERVICE + API_V1_TWEETS, contextId = "TweetClient", configuration = FeignConfiguration.class)
public interface TweetClient {
    @GetMapping(NOTIFICATION_TWEET_ID)
    NotificationTweetResponse getNotificationTweet(@PathVariable("tweetId") Long tweetId);
}
