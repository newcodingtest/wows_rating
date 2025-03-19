package com.wows.warship.repository;

import com.wows.warship.entity.BattlesHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattlesHistoryRepository extends JpaRepository<BattlesHistoryEntity, Long> {
}
