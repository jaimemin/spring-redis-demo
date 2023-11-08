package com.tistory.jaimemin.redisdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final String LEADER_BOARD_KEY = "leaderBoard";

    private final StringRedisTemplate redisTemplate;

    // zSet이 SortedSet
    public boolean setUserScore(String userId, int score) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(LEADER_BOARD_KEY, userId, score);

        return true;
    }

    public Long getUserRanking(String userId) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        Long rank = zSetOps.rank(LEADER_BOARD_KEY, userId);

        return rank;
    }

    public List<String> getTopRanks(int limit) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        Set<String> rangeSet = zSetOps.reverseRange(LEADER_BOARD_KEY, 0, limit - 1);

        return new ArrayList<>(rangeSet);
    }
}
