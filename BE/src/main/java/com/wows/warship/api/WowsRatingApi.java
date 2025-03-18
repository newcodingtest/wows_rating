package com.wows.warship.api;

import com.wows.warship.api.response.UserCheckResponse;
import com.wows.warship.dto.UserAccount;
import com.wows.warship.service.RatingStatsService;
import com.wows.warship.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WowsRatingApi {
    private final RatingStatsService ratingStatsService;


    private final UserAccountService userAccountService;


    /**
     * 유저 레이팅 조회
     * */
    @GetMapping("/rate/{nickname}")
    public void getUserRate(@PathVariable("nickname")String nickname){
        ratingStatsService.getRating(nickname);
    }

    /**
     * 유저 존재 유무 확인
     * */
    @GetMapping("/check/{nickname}")
    public UserCheckResponse validUser(@PathVariable("nickname")String nickname){
        return UserCheckResponse
                .from(userAccountService.isUserExist(nickname));
    }

    /**
     * 최근 전적 갱신
     * */
    @PatchMapping("/rate/{nickname}")
    public void updateUserRate(@PathVariable("nickname")String nickname){

    }


}
