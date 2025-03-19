package com.wows.warship.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class RatingCollectorTests {

    @Autowired
    private RatingCollector ratingCollector;

    @DisplayName("유저 전적정보를 수집 할 수 있다.")
    @Test
    public void should_be_return_battleHistory(){
        ratingCollector.getUserRating();
    }
}
