package com.wows.warship.service;

import com.wows.warship.dto.RatingDto;
import com.wows.warship.repository.WowsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatingStatsService {

    private final WowsRepository wowsRepository;

    public RatingDto getRating(String nickname){
        return null;
    }
}
