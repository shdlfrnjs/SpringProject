package com.example.jpademo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpademoApplicationTests {
    @Autowired
    private MusicRepository musicRepository;

    @Test
    void contextLoads() {
        System.out.println(musicRepository.findAll());
    }

}
