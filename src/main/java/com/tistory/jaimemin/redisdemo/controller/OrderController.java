package com.tistory.jaimemin.redisdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final String ORDER_EVENTS_STREAM = "order-events";

    private final StringRedisTemplate redisTemplate;

    @GetMapping("/order")
    public String order(@RequestParam String userId
            , @RequestParam String productId
            , @RequestParam String price) {
        redisTemplate.opsForStream().add(ORDER_EVENTS_STREAM, getFieldMap(userId, productId, price));

        return "ok";

    }

    private Map<String, String> getFieldMap(String userId, String productId, String price) {
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("userId", userId);
        fieldMap.put("productId", productId);
        fieldMap.put("price", price);

        return fieldMap;
    }
}
