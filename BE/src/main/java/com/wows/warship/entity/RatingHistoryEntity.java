package com.wows.warship.entity;

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
