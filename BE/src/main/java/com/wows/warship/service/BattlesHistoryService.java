package com.wows.warship.service;

import com.wows.warship.dto.BattleHistoryDto;
import com.wows.warship.entity.BaseTimeEntity;
import com.wows.warship.entity.BattlesHistoryEntity;
import com.wows.warship.repository.BattlesHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BattlesHistoryService {

    private final BattlesHistoryRepository battlesHistoryRepository;

    @Transactional
    public void save(List<BattleHistoryDto> battles, String accountId){
        battlesHistoryRepository.saveAll(battles.stream()
                .map(x -> BattlesHistoryEntity.from(x, accountId))
                .collect(Collectors.toList()));
    }
}
