package com.wows.warship.rate.api.response;


import lombok.*;

/**
 * 오버롤, 오늘하루, 7일, 한달
   승률,레이팅,
*/
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingStaticsResponse {
    /**
     * 전체 레이팅
     * */
    private Total tatal;

    /**
     * 오늘 레이팅
     * */
    private Today today;

    /**
     * 일주일 레이팅
     * */
    private Week week;

    /**
     * 한달 레이팅
     * */
    private Month month;

    private Over over;

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class Total {
        private int numOfGames;
        private int ratingScore;
        private double wins;
        private double killRate;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class Today {
        private int numOfGames;
        private int ratingScore;
        private double wins;
        private double killRate;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class Week {
        private int numOfGames;
        private int ratingScore;
        private double wins;
        private double killRate;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class Month {
        private int numOfGames;
        private int ratingScore;
        private double wins;
        private double killRate;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class Over {
        private int numOfGames;
        private int ratingScore;
        private double wins;
        private double killRate;
    }
}
