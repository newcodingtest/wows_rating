package com.wows.warship.dto;


import lombok.Builder;

@Builder
public class RatingDto {
    private String nickname;
    private int ratingScore;
    private int wins;
}
