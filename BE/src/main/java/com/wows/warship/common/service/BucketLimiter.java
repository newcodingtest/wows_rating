package com.wows.warship.common.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BucketLimiter {

    // 사용자별 레이팅 제한 저장 (nickname + accountId 별로 제한 적용)
    private final Map<String, Bucket> rateLimits = new ConcurrentHashMap<>();

    // IP 별로 레이팅 제한을 설정할 때 사용하는 메서드
    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.builder()
                        .capacity(1)    //최대요청 2회
                        .refillGreedy(1, Duration.ofMinutes(15)).build() //15분에 1개씩 토큰 생성
                )
                .build();
    }
    public Bucket compute(String accountId){
        return  rateLimits.computeIfAbsent(accountId, k -> createNewBucket());
    }



}
