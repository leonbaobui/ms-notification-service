package com.ms.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import main.java.com.leon.baobui.configuration.FeignConfiguration;

import static main.java.com.leon.baobui.constants.FeignConstants.WEBSOCKET_SERVICE;
import static main.java.com.leon.baobui.constants.PathConstants.API_V1_WEBSOCKET;

@CircuitBreaker(name = WEBSOCKET_SERVICE)
@FeignClient(name = WEBSOCKET_SERVICE, url = "${service.gateway-url}", path = "/", configuration = FeignConfiguration.class)
public interface WebsocketClient {
    @PostMapping(API_V1_WEBSOCKET)
    void send(@RequestParam("destination") String destination, @RequestBody Object request);

}
