package com.wows.warship.service;

import com.wows.warship.api.response.UserCheckResponse;
import com.wows.warship.dto.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class UserAccountServiceTests {

    @Autowired
    private UserAccountService userAccountService;


    @DisplayName("유저정보 존재여부를 확인할 수 있다.")
    @Test
    public void isUserExistsTest(){
        //given
        String nickname = "noCap_noSpot_noHelp_Fxxk";

        //when
        UserAccount isExist = userAccountService.isUserExist(nickname);

        //then
        assertEquals(nickname, isExist.getNickname());
    }
}
