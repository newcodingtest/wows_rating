package com.wows.warship.rate.api;

import com.wows.warship.common.exception.WowsErrorCode;
import com.wows.warship.common.exception.WowsException;
import com.wows.warship.common.service.BucketLimiter;
import com.wows.warship.rate.api.response.RatingStaticsResponse;
import com.wows.warship.rate.service.RatingService;
import com.wows.warship.rate.service.RatingStatsService;

import io.github.bucket4j.Bucket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RequiredArgsConstructor
@RestController
public class RatingApi {


    private final RatingStatsService ratingStatsService;
    private final RatingService ratingService;

    private final BucketLimiter bucketLimiter;

    /**
     * 유저 레이팅 통계 조회
     * */
    @GetMapping("/rate/{nickname}")
    public ResponseEntity<RatingStaticsResponse> getUserRate(@PathVariable("nickname")String nickname){
        RatingStaticsResponse ratingStaticsResponse = ratingStatsService.getRating(nickname);
        log.info("{}", ratingStaticsResponse);

        return ResponseEntity.ok(ratingStaticsResponse);
    }

    /**
     * 유저 레이팅 통계 갱신
     * */
    @PutMapping("/rate/{nickname}/{accountId}")
    public ResponseEntity<Void> updateUserRate(@PathVariable("nickname")String nickname,
                                               @PathVariable("accountId")String accountId){

        Bucket bucket = bucketLimiter.compute(accountId);

        if (!bucket.tryConsume(1)) {
            throw new WowsException.TooManyRequestsException(WowsErrorCode.TOO_MANY_REQUESTS, accountId);
        }

       ratingService.updateRating(nickname, accountId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
