package com.jiefang.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class LockApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockApplication.class, args);
        System.out.println("------LockApplication----");
    }

}
