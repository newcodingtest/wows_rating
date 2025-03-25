package com.wows.warship.rate.service;

import com.wows.warship.common.service.WowsApiService;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.service.BattlesHistoryService;
import com.wows.warship.rate.api.response.RatingStatics;
import com.wows.warship.account.domain.UserAccount;
import com.wows.warship.rate.domain.Rating;
import com.wows.warship.account.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 레이팅 서비스
 * */
@RequiredArgsConstructor
@Service
public class RatingStatsService {

    private final BattlesHistoryService battlesHistoryService;
    private final UserAccountService userAccountService;

    private final WowsApiService wowsApiService;


    /**
     * Today 데이터는 api 요청으로 + 과거 데이터는 db 조회로 (캐시 기간 20분 또는 갱신 주기 정책과 동기화 해야함)
     * */
    @Cacheable(value = "rating", key = "#nickname")
    public RatingStatics getRating(String nickname){
        UserAccount find = userAccountService.getRate(nickname);
        String accountId = find.getAccountId();

        /**
         * get api
         * */
        List<BattlesHistory> todayHistory = wowsApiService.getBattleHistoryFromOneDay(accountId).stream()
                .map(BattlesHistory::from)
                .collect(Collectors.toList());

        long todayRating = Rating.calculate(todayHistory, new HashMap<>(),1);

        /**
         * get db
         * */
        List<BattlesHistory> pastHistory = battlesHistoryService.getBattleHistory(accountId);
        long pastRating = find.getRatingScore();
        long weekRating = Rating.calculate(pastHistory, new HashMap<>(),7);
        long monthRating = Rating.calculate(pastHistory, new HashMap<>(),30);

        int overallRating = (int)(todayRating+pastRating)/2;


        RatingStatics.Overall overall = RatingStatics.Overall.builder()
                .ratingScore(overallRating)
                .build();

        RatingStatics.Today today = RatingStatics.Today.builder()
                .ratingScore((int)todayRating)
                .build();

        RatingStatics.Week week = RatingStatics.Week.builder()
                .ratingScore((int)weekRating)
                .build();

        RatingStatics.Month month = RatingStatics.Month.builder()
                .ratingScore((int)monthRating)
                .build();

        return RatingStatics.builder()
                .overall(overall)
                .today(today)
                .week(week)
                .month(month)
                .build();
    }

    /**
     * 유저 레이팅 새로 갱신
     *  
     * 갱신 시간 15분 제한
     * */
    private void updateRating(String nickname, Long accountId){
        //DB에서 가져온 어제 히스토리+API에서 가져온 현재히스토리

    }


    private void calculate(Long userId){
        
    }

}
