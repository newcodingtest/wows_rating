package com.wows.warship.api;

import com.wows.warship.service.RatingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WowsRatingApi {

    @Autowired
    private RatingStatsService ratingStatsService;

    @GetMapping("/rate/{nickname}")
    public void getRate(@PathVariable("nickname")String nickname){

    }

}
