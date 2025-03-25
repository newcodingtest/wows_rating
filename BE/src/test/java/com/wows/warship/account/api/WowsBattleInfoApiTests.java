package com.wows.warship.account.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wows.warship.common.exception.WowsErrorCode;
import com.wows.warship.rate.service.RatingStatsService;
import com.wows.warship.account.service.UserAccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public class WowsBattleInfoApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RatingStatsService ratingStatsService;

    @Autowired
    private UserAccountService userAccountService;


    @DisplayName("유저가 존재한다면 확인 할 수 있다.")
    @Test
    public void validUserSuccessTest() throws Exception {
        //given
        String nickname = "noCap_noSpot_noHelp_Fxxk";

        //when-then
        mockMvc.perform(get("/check/"+nickname)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andDo(print());
    }

    @DisplayName("유저가 존재하지 않는다면 Exception을 발생한다.")
    @Test
    public void validUserFailedTest() throws Exception {
        //given
        String nickname = "notExistNickname";

        //when-then
        mockMvc.perform(get("/check/" + nickname)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // 404 응답 확인
                .andExpect(jsonPath("$.message").value(WowsErrorCode.NOT_FOUNT_USER.getMessage())) // 에러 필드가 있는지 확인
                .andExpect(jsonPath("$.code").value(WowsErrorCode.NOT_FOUNT_USER.getCode())) // 예외 메시지 검증
                .andDo(print());
    }

}
