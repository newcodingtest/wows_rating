package com.wows.warship.common.api;

import com.wows.warship.rate.service.RatingStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WowsRatingApi {
    private final RatingStatsService ratingStatsService;



    /**
     * 최근 전적 갱신
     * */
    @PatchMapping("/rate/{nickname}")
    public void updateUserRate(@PathVariable("nickname")String nickname){

    }


}
