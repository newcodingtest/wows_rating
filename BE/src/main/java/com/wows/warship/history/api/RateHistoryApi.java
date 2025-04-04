package com.wows.warship.history.api;

import com.wows.warship.history.api.response.RateHistoryResponse;
import com.wows.warship.history.service.query.RatingHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RateHistoryApi {

    private final RatingHistoryService ratingHistoryService;
    @GetMapping("/history/{accountId}")
    private ResponseEntity<List<RateHistoryResponse>> getLatestHistory(@PathVariable("accountId")String accountId){
        List<RateHistoryResponse> response = ratingHistoryService.getRateHistory(accountId);
        log.info("ct: {}", response);
        return ResponseEntity.ok(response);
    }

}
