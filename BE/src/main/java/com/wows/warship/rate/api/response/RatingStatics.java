package com.wows.warship.rate.api.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 오버롤, 오늘하루, 7일, 한달
   승률,레이팅,
*/
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingStatics {
    private Overall overall;
    private Today today;
    private Week week;
    private Month month;
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Overall {
        private int ratingScore;
    }
    @Builder
    public static class Today {
        private int numOfGames;
        private int ratingScore;
        private int wins;
    }
    @Builder
    public static class Week {
        private int numOfGames;
        private int ratingScore;
        private int wins;
    }
    @Builder
    public static class Month {
        private int numOfGames;
        private int ratingScore;
        private int wins;
    }
}
