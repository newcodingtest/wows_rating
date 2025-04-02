package com.wows.warship.history.api;

import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.common.service.ShipInfoService;
import com.wows.warship.history.api.response.RateHistoryResponse;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.service.BattlesHistoryService;
import com.wows.warship.history.service.query.RatingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class RateHistoryApi {

    private final RatingHistoryService ratingHistoryService;
    @GetMapping("/history/{accountId}")
    private ResponseEntity<List<RateHistoryResponse>> getLatestHistory(@PathVariable("accountId")String accountId){
        return ResponseEntity.ok(ratingHistoryService.getRateHistory(accountId));
    }

}
