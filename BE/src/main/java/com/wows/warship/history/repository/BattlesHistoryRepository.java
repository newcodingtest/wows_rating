package com.wows.warship.history.repository;

import com.wows.warship.history.entity.BattlesHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BattlesHistoryRepository extends JpaRepository<BattlesHistoryEntity, Long> {

    List<BattlesHistoryEntity> findByAccountId(String accountId);
}
