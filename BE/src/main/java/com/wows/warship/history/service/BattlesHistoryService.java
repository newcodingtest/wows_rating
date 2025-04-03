package com.wows.warship.history.service;

import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.common.service.ShipInfoService;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.entity.BattlesHistoryEntity;
import com.wows.warship.history.repository.BattlesHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BattlesHistoryService {

    private final BattlesHistoryRepository battlesHistoryRepository;
    private final ShipInfoService shipInfoService;

    /**
     * 저장 시 (last_battle_time,accountId) 를 비교후에 저장.
     *
     * */
    //@Transactional
    public void save(List<BattlesHistory> battles, String accountId){
        List<BattlesHistoryEntity> existingHistory = battlesHistoryRepository.findByAccountId(accountId);

        if (existingHistory.size()>0){
            // 기존 last_battle_time 값들을 Set으로 저장
            Set<Long> existingTimes = existingHistory.stream()
                    .map(BattlesHistoryEntity::getLastBattleTime)
                    .collect(Collectors.toSet());

            // 중복되지 않은 데이터만 저장
            List<BattlesHistoryEntity> newBattles = battles.stream()
                    .filter(battle -> !existingTimes.contains(battle.getLastPlayTime()))
                    .filter(battle -> shipInfoService.getShipInfo().containsKey(battle.getShipNumber())) // Ship 정보가 있는 경우만 처리
                    .map(battle -> {
                        ShipInfo ship = shipInfoService.getShipInfo().get(battle.getShipNumber());
                        return BattlesHistoryEntity.from(battle, ship, accountId);
                    })
                    .collect(Collectors.toList());

            if (!newBattles.isEmpty()) {
                battlesHistoryRepository.saveAll(newBattles);
            }
        } else {

            List<BattlesHistoryEntity> newBattles = battles.stream()
                    .filter(battle -> shipInfoService.getShipInfo().containsKey(battle.getShipNumber())) // Ship 정보가 있는 경우만 처리
                    .map(battle -> {
                        ShipInfo ship = shipInfoService.getShipInfo().get(battle.getShipNumber());
                        return BattlesHistoryEntity.from(battle, ship, accountId);
                    })
                    .collect(Collectors.toList());

            battlesHistoryRepository.saveAll(newBattles);
        }

    }


    /**
     * 유저 히스토리 조회
     * */
    public List<BattlesHistory> getBattleHistory(String accountId){
        return battlesHistoryRepository.findByAccountId(accountId)
                .stream()
                .map(BattlesHistoryEntity::toModel)
                //.filter(battle -> battle.getMaxXp()<=0)
                .collect(Collectors.toList());
    }
}
