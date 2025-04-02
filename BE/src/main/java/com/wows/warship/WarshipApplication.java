package com.wows.warship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableFeignClients
@EnableScheduling
@EnableJpaAuditing
@EnableRedisRepositories
@SpringBootApplication
public class WarshipApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarshipApplication.class, args);
    }

}
