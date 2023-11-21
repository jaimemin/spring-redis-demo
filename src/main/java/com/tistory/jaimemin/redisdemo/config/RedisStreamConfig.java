package com.tistory.jaimemin.redisdemo.config;

import com.tistory.jaimemin.redisdemo.listener.OrderEventStreamListener;
import com.tistory.jaimemin.redisdemo.listener.PaymentEventStreamListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RedisStreamConfig {

    private final static String INSTANCE = "instance-1";

    private final static String ORDER_EVENTS_STREAM = "order-events";

    private final static String PAYMENT_EVENTS_STREAM = "payment-events";

    private final static String PAYMENT_SERVICE_GROUP = "payment-service-group";

    private final static String NOTIFICATION_SERVICE_GROUP = "notification-service-group";

    private final OrderEventStreamListener orderEventStreamListener;

    private final PaymentEventStreamListener paymentEventStreamListener;

    @Bean
    public Subscription subscription(RedisConnectionFactory factory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();
        /**
         * GROUP은 미리 생성해두기
         *
         * XGROUP CREATE order-events payment-service-group $ MKSTREAM
         * XGROUP CREATE order-events notification-service-group $ MKSTREAM
         * XGROUP CREATE payment-events notification-service-group $ MKSTREAM
         */
        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(factory, options);
        Subscription subscription = listenerContainer.receiveAutoAck(Consumer.from(PAYMENT_SERVICE_GROUP, INSTANCE),
                StreamOffset.create(ORDER_EVENTS_STREAM, ReadOffset.lastConsumed()), orderEventStreamListener);

        return subscription;
    }

    @Bean
    public Subscription notificationOrderSubscription(RedisConnectionFactory factory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();
        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(factory, options);
        Subscription subscription = listenerContainer.receiveAutoAck(Consumer.from(NOTIFICATION_SERVICE_GROUP, INSTANCE),
                StreamOffset.create(ORDER_EVENTS_STREAM, ReadOffset.lastConsumed()), orderEventStreamListener);

        return subscription;
    }

    @Bean
    public Subscription notificationPaymentSubscription(RedisConnectionFactory factory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();
        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(factory, options);
        Subscription subscription = listenerContainer.receiveAutoAck(Consumer.from(NOTIFICATION_SERVICE_GROUP, INSTANCE),
                StreamOffset.create(PAYMENT_EVENTS_STREAM, ReadOffset.lastConsumed()), paymentEventStreamListener);

        return subscription;
    }
}
