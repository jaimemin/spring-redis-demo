package com.tistory.jaimemin.redisdemo.controller;

import com.tistory.jaimemin.redisdemo.dto.ScoreDto;
import com.tistory.jaimemin.redisdemo.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rank")
public class RankingApiController {

    private final RankingService rankingService;

    @PostMapping
    public Boolean setScore(@RequestBody ScoreDto scoreDto) {
        return rankingService.setUserScore(scoreDto.getUserId(), scoreDto.getScore());
    }

    @GetMapping
    public Long getUserRank(@RequestParam String userId) {
        return rankingService.getUserRanking(userId);
    }

    @GetMapping("/top")
    public List<String> getTopRanks(@RequestParam int limit) {
        return rankingService.getTopRanks(limit);
    }
}
