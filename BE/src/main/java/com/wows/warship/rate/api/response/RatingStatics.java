package com.wows.warship.rate.api.response;


import lombok.Builder;
/**
 * 오버롤, 오늘하루, 7일, 한달
   승률,레이팅,
*/
@Builder
public class RatingStatics {
    private int ratingScore;
    private int wins;

    private Overall overall;
    private Today today;
    private Week week;
    private Month month;

    static class Overall {
        private int numOfGames;
        private int ratingScore;
        private int wins;
    }

    static class Today {
        private int numOfGames;
        private int ratingScore;
        private int wins;
    }

    static class Week {
        private int numOfGames;
        private int ratingScore;
        private int wins;
    }

    static class Month {
        private int numOfGames;
        private int ratingScore;
        private int wins;
    }
}
