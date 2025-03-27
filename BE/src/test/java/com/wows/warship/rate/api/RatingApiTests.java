package com.wows.warship.rate.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wows.warship.account.service.UserAccountService;
import com.wows.warship.common.exception.WowsErrorCode;
import com.wows.warship.common.service.BucketLimiter;
import com.wows.warship.rate.service.RatingService;
import com.wows.warship.rate.service.RatingStatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public class RatingApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RatingStatsService ratingStatsService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private BucketLimiter bucketLimiter;

    @DisplayName("유저 레이팅 통계를 조회 할 수 있다.")
    @Test
    public void getUserRateTest() throws Exception {
        //given
        String nickname = "noCap_noSpot_noHelp_Fxxk";
        mockMvc.perform(get("/check/"+nickname)
                .content(MediaType.APPLICATION_JSON_VALUE));

        //when-then
        mockMvc.perform(get("/rate/"+nickname)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());

    }

    @DisplayName("유저 레이팅 통계시 최대 2번, 15분에 1번씩 요청이 제한된다..")
    @Test
    public void updateUserRateTest() throws Exception {
        //given
        String nickname = "noCap_noSpot_noHelp_Fxxk";
        String accountId = "2020639284";
        mockMvc.perform(get("/check/"+nickname)
                .content(MediaType.APPLICATION_JSON_VALUE));

        //when-then
        mockMvc.perform(put("/rate/" + nickname+"/"+accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mockMvc.perform(put("/rate/" + nickname+"/"+accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(WowsErrorCode.TOO_MANY_REQUESTS.getMessage())) // 에러 필드가 있는지 확인
                .andExpect(jsonPath("$.code").value(WowsErrorCode.TOO_MANY_REQUESTS.getCode())) // 예외 메시지 검증
                .andDo(print());
    }

}
