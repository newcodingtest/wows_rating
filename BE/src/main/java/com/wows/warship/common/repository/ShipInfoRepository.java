package com.wows.warship.common.repository;

import com.wows.warship.common.entity.ShipInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipInfoRepository extends JpaRepository<ShipInfoEntity, Long> {
}
