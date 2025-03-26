package com.wows.warship.common.service;

import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.common.entity.ShipInfoEntity;
import com.wows.warship.common.repository.ShipInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShipInfoService {

    private final ShipInfoRepository shipInfoRepository;


    @Cacheable(value = "shipinfo", key = "#shipId")
    public Map<Long, ShipInfo> getShipInfo(){
        return shipInfoRepository.findAll().stream()
                .map(ShipInfoEntity::toModel)
                .collect(Collectors.toMap(ShipInfo::getShipId, shipInfo -> shipInfo));
    }
}
