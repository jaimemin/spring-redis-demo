package com.tistory.jaimemin.redisdemo.service;

import com.tistory.jaimemin.redisdemo.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final String KEY = "nameKey:";

    private final StringRedisTemplate redisTemplate;

    private final MockExternalApiService externalApiService;

    public UserProfile getUserProfile(String userId) throws InterruptedException {
        String userName = getUserName(userId);
        int userAge = externalApiService.getUserAge(userId);

        return new UserProfile(userName, userAge);
    }

    /**
     * Cache-Aside 패턴
     *
     * @param userId
     * @return
     * @throws InterruptedException
     */
    private String getUserName(String userId) throws InterruptedException {
        String userName = null;
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedName = ops.get(KEY + userId);

        if (StringUtils.isEmpty(cachedName)) {
            userName = externalApiService.getUserName(userId);
            ops.set(KEY + userId, userName, 5, TimeUnit.SECONDS);
        } else {
            userName = cachedName;
        }

        return userName;
    }
}
