package com.wows.warship.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wows.warship.common.feign.WowsApiClient;
import com.wows.warship.common.service.WowsApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class WowsApiServiceTests {

    @Mock
    private WowsApiClient wowsApiClient;

    @InjectMocks
    private WowsApiService testService;

    @DisplayName("샘플 테스트: 닉네임이 존재하는지 확인 할 수 있다.")
    @Test
    public void sampleTest() throws IOException {
        //given
        InputStream is = new FileInputStream("src/sample/account/account-list.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jnode = objectMapper.readTree(is);


        Map<String, Object> mockResponse = objectMapper.readValue(jnode.toString(),
                new TypeReference< Map<String, Object> >() {
                });

        String nickname = "noCap";

        //when
        Mockito.when(testService.getUserAccountInfo(nickname))
                .thenReturn(mockResponse);
        Map<String, Object> response = testService.getUserAccountInfo(nickname);

        ArrayList<Map<String, String>>actual = (ArrayList)response.get("data");

        //then
        assertEquals("noCap_noSpot_noHelp_Fxxk", actual.get(4).get("nickname"));
    }
}
