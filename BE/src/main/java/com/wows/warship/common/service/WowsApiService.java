package com.wows.warship.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wows.warship.common.feign.WowsApiClient;
import com.wows.warship.history.dto.BattleHistoryApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class WowsApiService {

    @Value("${wows.api.key}")
    private String applicationId;
    private final WowsApiClient wowsApiClient;

    private final ObjectMapper objectMapper;



    public  Map<String, Object>  getUserAccountInfo(String nickname) {
        String applicationId = "4a5f774ca91614ec9e42bdb76474af15";  // Application ID
        return wowsApiClient.getAccountList(applicationId, nickname);
    }

    public List<BattleHistoryApiResponse> getBattleHistoryFromOneDay(
                                                   String accountId) {
        Map<String, Object> response = wowsApiClient.getBattleHistory(applicationId, accountId);
        JsonNode jsonNode = objectMapper.valueToTree(response);
        List<BattleHistoryApiResponse> allHistory = new ArrayList<>();
        try {
            allHistory = objectMapper.readValue(jsonNode.get("data").get(accountId).toString(),
                    new TypeReference<List<BattleHistoryApiResponse>>() {
                    });
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return allHistory;
        }

        Collections.sort(allHistory);

        List<BattleHistoryApiResponse> todayHistory = new ArrayList<>();
        for (BattleHistoryApiResponse dto : allHistory){
            System.out.println(dto);
            if (diffTimeStamp(dto.getUpdated_at())>0){
                break;
            } else {
                todayHistory.add(dto);
            }
        }
        return todayHistory;
    }

    public long diffTimeStamp(long passTime){
        long nowTime = Instant.now().getEpochSecond();
        long diffTime = nowTime-passTime;

        long diffDays = TimeUnit.SECONDS.toDays(diffTime);
        return diffDays;
    }
}
