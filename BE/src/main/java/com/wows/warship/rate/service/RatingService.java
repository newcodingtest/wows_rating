package com.wows.warship.rate.service;

import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.service.BattlesHistoryService;
import com.wows.warship.rate.domain.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RatingService {

    private final BattlesHistoryService battlesHistoryService;


    public long getRate(String accountId){
        List<BattlesHistory> histories = battlesHistoryService.getBattleHistory(accountId);
        for (BattlesHistory history : histories){
           // int ratingScore = Rating.calculate(history);
        }
        return 0l;
    }
}
