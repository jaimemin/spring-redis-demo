package com.tistory.jaimemin.redisdemo;

import com.tistory.jaimemin.redisdemo.service.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class RankingTest {

    @Autowired
    private RankingService rankingService;

//    @Test
//    void insertScore() {
//        for (int i = 0; i < 1000000; i++) {
//            int score = (int) (Math.random() * 1000000);
//            String userId = "user_" + i;
//
//            rankingService.setUserScore(userId, score);
//        }
//    }

    @Test
    void getRanks() {
        rankingService.getTopRanks(1); // 의미없는 호출 (최초 호출은 오래 걸릴 수 있음)

        Instant before = Instant.now();
        Long userRank = rankingService.getUserRanking("user_100");
        Duration elapsed = Duration.between(before, Instant.now());

        System.out.println(String.format("Rank(%d) - Took %d ms", userRank, elapsed.getNano() / 1000000));

        before = Instant.now();
        List<String> topRanks = rankingService.getTopRanks(10);
        elapsed = Duration.between(before, Instant.now());

        System.out.println(String.format("Rank - Took %d ms", elapsed.getNano() / 1000000));
    }

    @Test
    void inMemorySortPerformance() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1000000; i++) {
            int score = (int) (Math.random() * 1000000);

            list.add(score);
        }

        Instant before = Instant.now();
        Collections.sort(list);
        Duration elapsed = Duration.between(before, Instant.now());

        System.out.println(String.format("%d ms", elapsed.getNano() / 1000000));
    }
}
