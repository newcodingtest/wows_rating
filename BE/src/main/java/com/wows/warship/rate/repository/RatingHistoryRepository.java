package com.wows.warship.rate.repository;

import com.wows.warship.history.entity.RatingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingHistoryRepository extends JpaRepository<RatingHistoryEntity, Long> {
}
