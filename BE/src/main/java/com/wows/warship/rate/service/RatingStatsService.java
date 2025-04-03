package com.wows.warship.rate.service;

import com.wows.warship.account.event.UpdatedUserRatingEvent;
import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.common.service.ShipInfoService;
import com.wows.warship.common.service.WowsApiService;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.event.CreatedBattleHistoryEvent;
import com.wows.warship.history.service.BattlesHistoryService;
import com.wows.warship.rate.api.response.RatingStaticsResponse;
import com.wows.warship.account.domain.UserAccount;
import com.wows.warship.rate.domain.Rating;
import com.wows.warship.account.service.UserAccountService;
import io.netty.channel.ConnectTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Today 데이터는 api 요청으로 + 과거 데이터는 db 조회로 (캐시 기간 20분 또는 갱신 주기 정책과 동기화 해야함)
     * */
    @Cacheable(value = "rating", key = "#nickname")
    public RatingStaticsResponse getRating(String nickname) {
        UserAccount find = userAccountService.getRate(nickname);
        String accountId = find.getAccountId();
        Map<Long, ShipInfo> shipInfoMap = shipInfoService.getShipInfo();

        // 처음 조회해서 DB에 히스토리가 없을 때
        if (find.getRatingScore() == 0) {
            log.info("db 에 없습니다.");
            List<BattlesHistory> apiHistory = fetchApiHistory(accountId);

            /**
             * 히스토리 저장
             * */
            applicationEventPublisher.publishEvent(CreatedBattleHistoryEvent.builder()
                    .accountId(accountId)
                    .battles(apiHistory)
                    .build());

            RatingStaticsResponse ratingStaticsResponse = createRatingStatics(calculateRatings(apiHistory,
                    shipInfoMap));

            /**
             * 레이팅 저장
             * */
            applicationEventPublisher.publishEvent(UpdatedUserRatingEvent.builder()
                    .accountId(find.getAccountId())
                    .rating(ratingStaticsResponse.getTatal().getRatingScore())
                    .build());

            return ratingStaticsResponse;
        }
        // 기존 데이터가 있는 경우
        log.info("db 에 있습니다.");
        RatingStaticsResponse ratingStaticsResponse = createRatingStatics(calculateRatingsForExistingUser(accountId, shipInfoMap));

        /**
         * 레이팅 저장
         * */
        applicationEventPublisher.publishEvent(UpdatedUserRatingEvent.builder()
                .accountId(find.getAccountId())
                .rating(ratingStaticsResponse.getTatal().getRatingScore())
                .build());

        return ratingStaticsResponse;
    }

    // API에서 전적 조회 및 변환
    private List<BattlesHistory> fetchApiHistory(String accountId) {
        return wowsApiService.getBattleHistory(accountId).stream()
                .map(BattlesHistory::from)
                .collect(Collectors.toList());
    }
    // 새롭게 가져온 데이터로 기간 별 레이팅 산정


    // 기존 데이터와 함께 레이팅 산정
    private Map<Integer, Rating> calculateRatingsForExistingUser(String accountId, Map<Long, ShipInfo> shipInfoMap) {
        List<BattlesHistory> todayHistory = wowsApiService.getBattleHistoryFromOneDay(accountId).stream()
                .map(BattlesHistory::from)
                .collect(Collectors.toList());

        List<BattlesHistory> pastHistory = battlesHistoryService.getBattleHistory(accountId);
        List<BattlesHistory> combinedHistory = new ArrayList<>(pastHistory.size() + todayHistory.size());
        combinedHistory.addAll(pastHistory);
        combinedHistory.addAll(todayHistory);

        Rating todayRating = Rating.getToday(todayHistory, shipInfoMap);
        Rating weekRating = Rating.getWeek(pastHistory, shipInfoMap);
        Rating monthRating = Rating.getMonth(pastHistory, shipInfoMap);
        Rating overallRating = Rating.getOver(pastHistory, shipInfoMap);
        Rating totalRating = Rating.getTotal(combinedHistory, shipInfoMap);

        return Map.of(
                0,totalRating,
                1, todayRating,
                7, weekRating,
                30, monthRating,
                10000, overallRating
        );
    }

    private Map<Integer, Rating> calculateRatings(List<BattlesHistory> history, Map<Long, ShipInfo> shipInfoMap) {
        return Map.of(
                0, Rating.getTotal(history, shipInfoMap),
                1, Rating.getToday(history, shipInfoMap),
                7, Rating.getWeek(history, shipInfoMap),
                30, Rating.getMonth(history, shipInfoMap),
                10000, Rating.getOver(history, shipInfoMap));
    }

    private RatingStaticsResponse createRatingStatics(Map<Integer, Rating> ratings) {

        Rating total = ratings.get(0);
        Rating today = ratings.get(1);
        Rating week = ratings.get(7);
        Rating month = ratings.get(30);
        Rating overall = ratings.get(10000);

        RatingStaticsResponse.Total totalResponse = RatingStaticsResponse.Total.builder()
                .ratingScore(total.getRatingScore())
                .numOfGames(total.getBattleCount())
                .wins(total.getWinRate())
                .killRate(total.getKillRate())
                .build();

        RatingStaticsResponse.Today todayResponse = RatingStaticsResponse.Today.builder()
                .ratingScore(today.getRatingScore())
                .numOfGames(today.getBattleCount())
                .wins(today.getWinRate())
                .killRate(today.getKillRate())
                .build();

        RatingStaticsResponse.Week weekResponse = RatingStaticsResponse.Week.builder()
                .ratingScore(week.getRatingScore())
                .numOfGames(week.getBattleCount())
                .wins(week.getWinRate())
                .killRate(week.getKillRate())
                .build();

        RatingStaticsResponse.Month monthResponse = RatingStaticsResponse.Month.builder()
                .ratingScore(month.getRatingScore())
                .numOfGames(month.getBattleCount())
                .wins(month.getWinRate())
                .killRate(month.getKillRate())
                .build();


        RatingStaticsResponse.Over overResponse = RatingStaticsResponse.Over.builder()
                .ratingScore(overall.getRatingScore())
                .numOfGames(overall.getBattleCount())
                .wins(overall.getWinRate())
                .killRate(overall.getKillRate())
                .build();

        return RatingStaticsResponse.builder()
                .tatal(totalResponse)
                .today(todayResponse)
                .week(weekResponse)
                .month(monthResponse)
                .over(overResponse)
                .build();
    }

}
