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
    private Overall overall;
    private Today today;
    private Week week;
    private Month month;

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class Overall {
        private int ratingScore;
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
    }
}
