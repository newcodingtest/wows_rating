package com.wows.warship.history.service;

import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.entity.BattlesHistoryEntity;
import com.wows.warship.history.repository.BattlesHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BattlesHistoryService {

    private final BattlesHistoryRepository battlesHistoryRepository;

    @Transactional
    public void save(List<BattlesHistory> battles, String accountId){
        battlesHistoryRepository.saveAll(battles.stream()
                .map(x -> BattlesHistoryEntity.from(x, accountId))
                .collect(Collectors.toList()));
    }

    @Cacheable(value = "history", key = "#accountId")
    public List<BattlesHistory> getBattleHistory(String accountId){
        return battlesHistoryRepository.findByAccountId(accountId)
                .stream()
                .map(BattlesHistoryEntity::toModel)
                .collect(Collectors.toList());
    }

    public void updateHistory(String accountId){

    }
}
