package com.sparta.hanghaespringhomework1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanghaeSpringHomework1Application {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeSpringHomework1Application.class, args);
    }

}
