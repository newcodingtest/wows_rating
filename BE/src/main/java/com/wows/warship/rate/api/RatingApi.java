package com.wows.warship.rate.api;

import com.wows.warship.rate.service.RatingStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class RatingApi {

    private final RatingStatsService ratingStatsService;

    /**
     * 유저 레이팅 조회
     * */
    @GetMapping("/rate/{nickname}")
    public void getUserRate(@PathVariable("nickname")String nickname){
        ratingStatsService.getRating(nickname);
    }


}
