package com.wows.warship.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BattlesHistoryEntity extends BaseTimeEntity {

    @Id
    private Long id;

    private String shipName;

    private int tier;

    private int ratingScore;
}
