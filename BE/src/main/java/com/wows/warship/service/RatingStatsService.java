package com.wows.warship.service;

import com.wows.warship.dto.RatingDto;
import com.wows.warship.dto.UserAccount;
import com.wows.warship.repository.RatingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 레이팅 서비스
 * */
@RequiredArgsConstructor
@Service
public class RatingStatsService {

    private final RatingHistoryRepository ratingHistoryRepository;

    private final UserAccountService userAccountService;


    /**
     * DB에 있는 유저 레이팅을 가져온다.
     * */
    @Cacheable(value = "rating", key = "#nickname")
    public RatingDto getRating(String nickname){
        UserAccount find = userAccountService.getRate(nickname);

        return RatingDto.builder()
                .nickname(find.getNickname())
                .ratingScore(find.getRatingScore())
                .wins(find.getWins())
                .build();
    }

    /**
     * 유저 레이팅 새로 갱신
     *  
     * 갱신 시간 15분 제한
     * */
    private void updateRating(Long userId){

    }


    private void calculate(Long userId){

    }

}
