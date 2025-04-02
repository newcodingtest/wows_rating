package com.wows.warship.rate.service;

import com.wows.warship.common.service.WowsApiService;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.service.BattlesHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RatingService {

    private final WowsApiService wowsApiService;
    private final BattlesHistoryService battlesHistoryService;


    public long getRate(String accountId){
        List<BattlesHistory> histories = battlesHistoryService.getBattleHistory(accountId);
        for (BattlesHistory history : histories){
           // int ratingScore = Rating.calculate(history);
        }
        return 0l;
    }

    /**
     * 유저 레이팅 새로 갱신
     *
     * 갱신 시간 15분 제한
     * */

    @CacheEvict(value = "rating", key = "#nickname")
    public void updateRating(String nickname, String accountId){
        //DB에서 가져온 어제 히스토리+API에서 가져온 현재히스토리
        List<BattlesHistory> todayHistory = wowsApiService.getBattleHistoryFromOneDay(accountId).stream()
                .map(BattlesHistory::from)
                .collect(Collectors.toList());

        battlesHistoryService.save(todayHistory, accountId);
    }

    public void calculate(BattlesHistory battlesHistory){

    }
}
