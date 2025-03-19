package com.wows.warship.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true) //정의되지 않은 포멧은 무시
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BattleHistoryDto implements Comparable<BattleHistoryDto> {
    @JsonProperty("pvp")
    @JsonAlias({"pvp_div2", "pvp_div3", "pvp_solo"})
    //,"pvp_div2", "pvp_div3", "pvp_solo"
    private PvpStats pvp;
    @JsonProperty("updated_at")
    private long updated_at;
    @JsonProperty("last_battle_time")
    private long last_battle_time;
    @JsonProperty("ship_id")
    private long ship_id;

    @Override
    public int compareTo(BattleHistoryDto o) {
        return (int)(o.last_battle_time - this.last_battle_time);
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
         * 팀 전체 데미지 점수
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