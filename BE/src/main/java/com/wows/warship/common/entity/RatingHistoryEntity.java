package com.wows.warship.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RatingHistoryEntity extends BaseTimeEntity {

    @Id
    private Long seq;

    private String nickname;

    private int ratingScore;

    private int wins;

}
