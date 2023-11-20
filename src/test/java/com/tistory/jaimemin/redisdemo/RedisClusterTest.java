package com.tistory.jaimemin.redisdemo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisClusterTest {

    @Autowired
    private RedisTemplate redisTemplate;

    private String dummyValue = "banana";

    @Test
    void setValues() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        for (int i = 0; i < 1000; i++) {
            String key = String.format("name:%d", i); // name:1

            ops.set(key, dummyValue);
        }
    }

    @Test
    void getValues() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        for (int i = 0; i < 1000; i++) {
            String key = String.format("name:%d", i); // name:1
            String value = ops.get(key);

            assertThat(value.equals(dummyValue));
        }
    }
}
