package com.wows.warship.rate.service;

import com.wows.warship.rate.api.response.RatingStatics;
import com.wows.warship.account.domain.UserAccount;
import com.wows.warship.rate.repository.RatingHistoryRepository;
import com.wows.warship.common.service.UserAccountService;
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
    public RatingStatics getRating(String nickname){
        UserAccount find = userAccountService.getRate(nickname);

//        return RatingHistoryDto.builder()
//                .nickname(find.getNickname())
//                .ratingScore(find.getRatingScore())
//                .wins(find.getWins())
//                .build();
        return null;
    }

    /**
     * 유저 레이팅 새로 갱신
     *  
     * 갱신 시간 15분 제한
     * */
    private void updateRating(String nickname, Long accountId){
        //DB에서 가져온 어제 히스토리+API에서 가져온 현재히스토리

    }


    private void calculate(Long userId){
        
    }

}
