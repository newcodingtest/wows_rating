package com.wows.warship.repository;

import com.wows.warship.entity.RatingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WowsRepository extends JpaRepository<RatingHistoryEntity, Long> {
}
