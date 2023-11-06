package com.tistory.jaimemin.redisdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MockExternalApiService {

    public String getUserName(String userId) throws InterruptedException {
        // 외부 서비스나 DB 호출
        Thread.sleep(500);

        log.info("Getting user name from other service..");

        if ("A".equals(userId)) {
            return "Adam";
        } else if ("B".equals(userId)) {
            return "Bob";
        }

        return "";
    }

    public int getUserAge(String userId) throws InterruptedException {
        // 외부 서비스나 DB 호출
        Thread.sleep(500);

        log.info("Getting user age from other service..");

        if ("A".equals(userId)) {
            return 28;
        } else if ("B".equals(userId)) {
            return 32;
        }

        return 0;
    }
}
