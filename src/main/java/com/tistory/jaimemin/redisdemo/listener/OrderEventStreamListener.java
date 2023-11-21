package com.tistory.jaimemin.redisdemo.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderEventStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

    private final static String PAYMENT_EVENTS_STREAM = "payment-events";

    private final StringRedisTemplate redisTemplate;

    private int paymentProcessId = 0;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        Map map = message.getValue();
        String userId = (String) map.get("userId");
        String productId = (String) map.get("productId");
        String price = (String) map.get("price");

        // 결제 관련 로직 처리

        // 결제 완료 이벤트 발행
        String paymentIdStr = Integer.toString(paymentProcessId++);
        Map<String, String> fieldMap = getFieldMap(userId, productId, price, paymentIdStr);

        redisTemplate.opsForStream().add(PAYMENT_EVENTS_STREAM, fieldMap);

        System.out.println(String.format("[Order consumed] Created payment: %s", paymentIdStr));
    }

    private Map<String, String> getFieldMap(String userId, String productId, String price, String paymentIdStr) {
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("userId", userId);
        fieldMap.put("productId", productId);
        fieldMap.put("price", price);
        fieldMap.put("paymentProcessId", paymentIdStr);

        return fieldMap;
    }
}
