package com.wows.warship.history.service.query;

import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.common.service.ShipInfoService;
import com.wows.warship.history.api.response.RateHistoryResponse;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.service.BattlesHistoryService;
import com.wows.warship.rate.domain.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RatingHistoryService {

    private final BattlesHistoryService battlesHistoryService;
    private final ShipInfoService shipInfoService;


    public List<RateHistoryResponse> getRateHistory(String accountId){
        List<BattlesHistory> histories = battlesHistoryService.getBattleHistory(accountId);
        List<RateHistoryResponse> responses = histories.stream()
                .map(h -> {
                    ShipInfo shipInfo = shipInfoService.getShipInfo().get(h.getShipNumber());
                    Rating rating = Rating.calculate(h, shipInfo);
                    return RateHistoryResponse.from(h, shipInfo, rating);
                })
                .collect(Collectors.toList());

        return responses;
    }
}
