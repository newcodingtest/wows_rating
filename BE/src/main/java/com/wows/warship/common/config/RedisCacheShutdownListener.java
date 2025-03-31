package com.wows.warship.common.config;

import jakarta.annotation.PreDestroy;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private final StringRedisTemplate redisTemplate;

    public RedisCacheShutdownListener(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("🛑 Spring 애플리케이션 종료 → Redis 캐시 삭제 시작!");
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
}