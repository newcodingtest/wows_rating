package com.wows.warship.repository;

import com.wows.warship.entity.ShipInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipInfoRepository extends JpaRepository<ShipInfoEntity, Long> {
}
