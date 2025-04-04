package com.wows.warship.history.service.query;

import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.common.service.ShipInfoService;
import com.wows.warship.history.api.response.RateHistoryResponse;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.service.BattlesHistoryService;
import com.wows.warship.rate.domain.Rating;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RatingHistoryService {

    private final BattlesHistoryService battlesHistoryService;
    private final ShipInfoService shipInfoService;


    public List<RateHistoryResponse> getRateHistory(String accountId){
        List<BattlesHistory> histories = battlesHistoryService.getBattleHistory(accountId);
        log.info("size: {}", histories.size());
        for (BattlesHistory bh : histories){
            System.out.println("bh = " + bh);
        }
        List<RateHistoryResponse> responses = histories.stream()
                .map(h -> {
                    ShipInfo shipInfo = shipInfoService.getShipInfo().get(h.getShipNumber());
                    Rating rating = Rating.calculate(h, shipInfo);
                    log.info("rating: {}", rating);
                    return RateHistoryResponse.from(h, shipInfo, rating);
                })
                .collect(Collectors.toList());

        log.info("responses: {}", responses);
        return responses;
    }
}
