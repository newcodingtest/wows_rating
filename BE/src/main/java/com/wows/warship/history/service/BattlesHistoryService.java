package com.wows.warship.history.service;

import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.entity.BattlesHistoryEntity;
import com.wows.warship.history.repository.BattlesHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BattlesHistoryService {

    private final BattlesHistoryRepository battlesHistoryRepository;

    /**
     * 저장 시 (last_battle_time,accountId) 를 비교후에 저장.
     *
     * */
    @Async
    public void save(List<BattlesHistory> battles, String accountId){
      saveDB(battles,accountId);
    }
    @Transactional
    private void saveDB(List<BattlesHistory> battles, String accountId){
        List<BattlesHistoryEntity> existingHistory = battlesHistoryRepository.findByAccountId(accountId);

        // 기존 last_battle_time 값들을 Set으로 저장
        Set<Long> existingTimes = existingHistory.stream()
                .map(BattlesHistoryEntity::getLastBattleTime)
                .collect(Collectors.toSet());

        // 중복되지 않은 데이터만 저장
        List<BattlesHistoryEntity> newBattles = battles.stream()
                .filter(battle -> !existingTimes.contains(battle.getLastPlayTime()))
                .map(battle -> BattlesHistoryEntity.from(battle, accountId))
                .collect(Collectors.toList());

        if (!newBattles.isEmpty()) {
            battlesHistoryRepository.saveAll(newBattles);
        }
    }

    @Cacheable(value = "history", key = "#accountId")
    public List<BattlesHistory> getBattleHistory(String accountId){
        return battlesHistoryRepository.findByAccountId(accountId)
                .stream()
                .map(BattlesHistoryEntity::toModel)
                .filter(battle -> battle.getMaxXp()>0)
                .collect(Collectors.toList());
    }

    public void updateHistory(String accountId){

    }
}
