package com.wows.warship.account.service;

import com.wows.warship.account.service.UserAccountService;
import com.wows.warship.account.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class UserAccountServiceTests {

    @Autowired
    private UserAccountService userAccountService;


    @DisplayName("유저 정보들을 모두 확인 할 수 있다.")
    @Test
    public void getAccountsTest(){
        //given
        String nickname1 = "noCap_noSpot_noHelp_Fxxk";
        userAccountService.isUserExist(nickname1);
        String nickname2 = "xYAMAT0x";
        userAccountService.isUserExist(nickname2);

        //when
        List<UserAccount> accounts = userAccountService.getAccounts();

        boolean nickname1Exists = false;
        boolean nickname2Exists = false;

        for (UserAccount account : accounts) {
            if (account.getNickname().equals(nickname1)) {
                nickname1Exists = true;
            }
            if (account.getNickname().equals(nickname2)) {
                nickname2Exists = true;
            }
        }

        // then
        assertTrue(nickname1Exists);
        assertTrue(nickname2Exists);
    }

    @DisplayName("유저 정보를 확인 할 수 있다.")
    @Test
    public void getRateTest(){
        //given
        String nickname = "noCap_noSpot_noHelp_Fxxk";
        userAccountService.isUserExist(nickname);

        //when
        UserAccount account = userAccountService.getRate(nickname);
        System.out.println("account = " + account);
        //then
        assertEquals(nickname, account.getNickname());
    }

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
