package com.wows.warship.service;

import com.wows.warship.dto.RatingDto;
import com.wows.warship.dto.UserAccount;
import com.wows.warship.repository.WowsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatingStatsService {

    private final WowsRepository wowsRepository;

    private final UserAccountService userAccountService;


    @Cacheable(value = "rating", key = "#nickname")
    public RatingDto getRating(String nickname){

        userAccountService.regist(UserAccount.builder()
                                    .nickname(nickname)
                                    .build());


        return null;
    }

}
