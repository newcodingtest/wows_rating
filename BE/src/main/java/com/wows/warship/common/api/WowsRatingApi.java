package com.wows.warship.common.api;

import com.wows.warship.common.api.response.UserCheckResponse;
import com.wows.warship.rate.service.RatingStatsService;
import com.wows.warship.common.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WowsRatingApi {
    private final RatingStatsService ratingStatsService;

    /**
     * 유저 레이팅 조회
     * */
    @GetMapping("/rate/{nickname}")
    public void getUserRate(@PathVariable("nickname")String nickname){
        ratingStatsService.getRating(nickname);
    }



    /**
     * 최근 전적 갱신
     * */
    @PatchMapping("/rate/{nickname}")
    public void updateUserRate(@PathVariable("nickname")String nickname){

    }


}
