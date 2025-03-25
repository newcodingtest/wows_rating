package com.wows.warship.history.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true) //정의되지 않은 포멧은 무시
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BattleHistoryApiResponse implements Comparable<BattleHistoryApiResponse> {
    @JsonProperty("pvp")
    private PvpStats pvp;
    @JsonProperty("updated_at")
    private long updated_at;
    @JsonProperty("last_battle_time")
    private long last_battle_time;
    @JsonProperty("ship_id")
    private long ship_id;

    @JsonProperty("rank_solo")
    private DetailedStatDto rank_solo;

    @JsonProperty("pvp_div2")
    private DetailedStatDto pvp_div2;

    @JsonProperty("pvp_div3")
    private DetailedStatDto pvp_div3;

    @JsonProperty("pvp_solo")
    private DetailedStatDto pvp_solo;

    @Override
    public int compareTo(BattleHistoryApiResponse o) {
        return (int)(o.last_battle_time - this.last_battle_time);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @ToString
    public class DetailedStatDto {
        @JsonProperty("max_xp")
        int max_xp;

        @JsonProperty("updated_at")
        private long updated_at;
        @JsonProperty("last_battle_time")
        private long last_battle_time;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @ToString
    public static class PvpStats {
        /**
         * 나의 점령 점수
         * */
        @JsonProperty("capture_points")
        public int capturePoints;

        @JsonProperty("max_xp")
        private int max_xp;
        /**
         * 승리 수
         * */
        public int wins;
        /**
         * 패배 수
         * */
        public int losses;
        /**
         * 판수
         * */
        public int battles;

        /**
         * 나의 스팟 점수
         * */
        @JsonProperty("max_damage_scouting")
        public int maxDamageScouting;

        /**
         * 팀 점령 점수
         * */
        @JsonProperty("team_capture_points")
        public int teamCapturePoints;

        public int frags;

        /**
         * 팀 전체 스팟 점수
         * */
        @JsonProperty("damage_scouting")
        public int damageScouting;

        /**
         * 나의 딜점수
         * */
        @JsonProperty("max_damage_dealt")
        public int maxDamageDealt;

        /**
         * 나에게 적팀이 쏜 유효 점수
         * */
        @JsonProperty("max_total_agro")
        public int maxTotalAgro;
    }
}