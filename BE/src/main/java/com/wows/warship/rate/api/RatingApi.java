package com.wows.warship.rate.api;

import com.wows.warship.rate.api.response.RatingStatics;
import com.wows.warship.rate.service.RatingStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class RatingApi {

    private final RatingStatsService ratingStatsService;

    /**
     * 유저 레이팅 조회
     * */
    @GetMapping("/rate/{nickname}")
    public ResponseEntity<RatingStatics> getUserRate(@PathVariable("nickname")String nickname){
        RatingStatics ratingStatics = ratingStatsService.getRating(nickname);
        log.info("{}", ratingStatics);

        return ResponseEntity.ok(ratingStatics);
    }


}
