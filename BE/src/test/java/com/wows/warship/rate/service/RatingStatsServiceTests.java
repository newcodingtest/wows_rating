package com.wows.warship.rate.service;

import com.wows.warship.account.service.UserAccountService;
import com.wows.warship.rate.api.response.RatingStaticsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
@ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true")
@ActiveProfiles("test")
@SpringBootTest
public class RatingStatsServiceTests {

    @Autowired
    private RatingStatsService ratingStatsService;
    @Autowired
    private UserAccountService userAccountService;


    @DisplayName("유저 레이팅을 확인 할 수 있다.")
    @Test
    public void getRatingTest(){
        //given
        String nickname = "noCap_noSpot_noHelp_Fxxk";
        //String nickname = "xYAMAT0x";
        userAccountService.isUserExist(nickname);

        //when
        RatingStaticsResponse ratingStaticsResponse = ratingStatsService.getRating(nickname);

        //then
        System.out.println("ratingStatics = " + ratingStaticsResponse);
    }

}
