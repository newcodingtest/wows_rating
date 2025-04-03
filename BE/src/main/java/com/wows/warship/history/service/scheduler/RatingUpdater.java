package com.wows.warship.history.service.scheduler;

import com.wows.warship.account.domain.UserAccount;
import com.wows.warship.account.service.UserAccountService;
import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.common.service.ShipInfoService;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.service.BattlesHistoryService;
import com.wows.warship.rate.domain.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RatingUpdater {

    private final UserAccountService userAccountService;
    private final BattlesHistoryService battlesHistoryService;

    private final ShipInfoService shipInfoService;

    @Scheduled(cron = "0 0 5 * * *")
    public void updateRating(){
        List<UserAccount> users = userAccountService.getAccounts();
        Map<Long, ShipInfo> shipInfoMap = shipInfoService.getShipInfo();
        for (UserAccount user : users){
            List<BattlesHistory> histories = battlesHistoryService.getBattleHistory(user.getAccountId());

            Rating rating = Rating.calculatesAverage(histories, shipInfoMap, 10000);

            userAccountService.uppateRate(user.getNickname(), rating.getRatingScore());
        }
    }
}
