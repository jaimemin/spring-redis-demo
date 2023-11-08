package com.tistory.jaimemin.redisdemo;

import com.tistory.jaimemin.redisdemo.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@RequiredArgsConstructor
public class RedisDemoApplication implements CommandLineRunner {

    private final ChatService chatService;

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    /**
     * pub/sub 실습을 위해
     *
     * @param args incoming main method arguments
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started...");

        chatService.enterChatRoom("chat");
    }
}
