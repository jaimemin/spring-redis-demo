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
public class PaymentEventStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        Map map = message.getValue();
        String userId = (String) map.get("userId");
        String paymentProcessId = (String) map.get("paymentProcessId");

        // 결제 완료 건에 대한 SMS 발송 처리
        System.out.println(String.format("[Payment consumed] userId: %s paymentProcessId: %s", userId, paymentProcessId));
    }
}
