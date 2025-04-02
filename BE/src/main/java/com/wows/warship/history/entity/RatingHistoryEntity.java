package com.wows.warship.history.entity;

import com.wows.warship.common.entity.BaseTimeEntity;
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
