package com.wows.warship.extract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
public class PvpStats {
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
