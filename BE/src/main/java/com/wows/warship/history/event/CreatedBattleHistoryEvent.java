package com.wows.warship.history.event;

import com.wows.warship.history.domain.BattlesHistory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreatedBattleHistoryEvent {
    String accountId;
    List<BattlesHistory> battles;
}
