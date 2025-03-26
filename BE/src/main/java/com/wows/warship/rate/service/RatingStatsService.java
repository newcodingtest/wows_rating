package com.wows.warship.rate.service;

import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.common.service.ShipInfoService;
import com.wows.warship.common.service.WowsApiService;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.service.BattlesHistoryService;
import com.wows.warship.rate.api.response.RatingStatics;
import com.wows.warship.account.domain.UserAccount;
import com.wows.warship.rate.domain.Rating;
import com.wows.warship.account.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 레이팅 서비스
 * */
@Slf4j
@RequiredArgsConstructor
@Service
public class RatingStatsService {

    private final BattlesHistoryService battlesHistoryService;
    private final UserAccountService userAccountService;

    private final WowsApiService wowsApiService;

    private final ShipInfoService shipInfoService;


    /**
     * Today 데이터는 api 요청으로 + 과거 데이터는 db 조회로 (캐시 기간 20분 또는 갱신 주기 정책과 동기화 해야함)
     * */
    @Cacheable(value = "rating", key = "#nickname")
    public RatingStatics getRating(String nickname) {
        UserAccount find = userAccountService.getRate(nickname);
        String accountId = find.getAccountId();
        Map<Long, ShipInfo> shipInfoMap = shipInfoService.getShipInfo();

        // 처음 조회해서 DB에 히스토리가 없을 때
        if (find.getRatingScore() == 0) {
            List<BattlesHistory> apiHistory = fetchApiHistory(accountId);
            battlesHistoryService.save(apiHistory, accountId);
            return createRatingStatics(calculateRatings(apiHistory, shipInfoMap));
        }

        // 기존 데이터가 있는 경우
        return createRatingStatics(calculateRatingsForExistingUser(find, accountId, shipInfoMap));
    }

    // API에서 전적 조회 및 변환
    private List<BattlesHistory> fetchApiHistory(String accountId) {
        return wowsApiService.getBattleHistory(accountId).stream()
                .map(BattlesHistory::from)
                .collect(Collectors.toList());
    }
    // 새롭게 가져온 데이터로 기간 별 레이팅 산정
    private Map<Integer, Long> calculateRatings(List<BattlesHistory> history, Map<Long, ShipInfo> shipInfoMap) {
        return Stream.of(1, 7, 10000)
                .collect(Collectors.toMap(days -> days, days -> (long)Rating.calculate(history, shipInfoMap, days).getRatingScore()));
    }

    // 기존 데이터와 함께 레이팅 산정
    private Map<Integer, Long> calculateRatingsForExistingUser(UserAccount user, String accountId, Map<Long, ShipInfo> shipInfoMap) {
        List<BattlesHistory> todayHistory = wowsApiService.getBattleHistoryFromOneDay(accountId).stream()
                .map(BattlesHistory::from)
                .collect(Collectors.toList());

        List<BattlesHistory> pastHistory = battlesHistoryService.getBattleHistory(accountId);
        Rating todayRating = Rating.calculate(todayHistory, shipInfoMap, 1);
        int pastRating = user.getRatingScore();
        Rating weekRating = Rating.calculate(pastHistory, shipInfoMap, 7);
        Rating monthRating = Rating.calculate(pastHistory, shipInfoMap, 10000);
        int overallRating = (int) ((todayRating.getRatingScore() + pastRating) / 2);

        return Map.of(
                1, (long)todayRating.getRatingScore(),
                7, (long)weekRating.getRatingScore(),
                10000, (long) overallRating // overallRating == monthRating
        );
    }

    private RatingStatics createRatingStatics(Map<Integer, Long> ratings) {
        return RatingStatics.builder()
                .overall(RatingStatics.Overall.builder().ratingScore(ratings.get(10000).intValue()).build())
                .today(RatingStatics.Today.builder().ratingScore(ratings.get(1).intValue()).build())
                .week(RatingStatics.Week.builder().ratingScore(ratings.get(7).intValue()).build())
                .month(RatingStatics.Month.builder().ratingScore(ratings.get(10000).intValue()).build()) // overall과 동일
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
