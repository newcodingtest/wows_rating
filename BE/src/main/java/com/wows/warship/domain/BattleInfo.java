package com.wows.warship.domain;

public class BattleInfo {
    /**
     * 배 이름
     * */
    private Long shipName;
    /**
     * 배 티어
     * */
    private int tier;
    /**
     * 레이팅 점수
     * */
    private int ratingScore;
    /**
     * 계정 아이디
     * */
    private String accountId;
    /**
     * 마지막 플레이 시간
     * */
    private Long lastBattleTime;
    /**
     * 승리
     * */
    private int wins;
    /**
     * 패배
     * */
    private int losses;
    /**
     * 플레이 횟수
     * */
    private int battles;
    /**
     * 스팟 점수
     * */
    private int spotScore;
    /**
     * 캡 점수
     * */
    private int capScore;
    /**
     * 킬
     * */
    private int kill;
    /**
     * 데미지
     * */
    private int damage;
    /**
     * 데미지
     * */
    private int tankingScore;
}
