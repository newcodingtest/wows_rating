package com.wows.warship.extract;


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
public class ShipDataDto implements Comparable<ShipDataDto> {
    @JsonProperty("pvp")
    private PvpStats pvp;

    @JsonProperty("updated_at")
    private long updated_at;
    @JsonProperty("last_battle_time")
    private long last_battle_time;
    @JsonProperty("ship_id")
    private long ship_id;

    @JsonProperty("rank_solo")
    private DetailedStatsDto rank_solo;

    @JsonProperty("pvp_div2")
    private DetailedStatsDto pvp_div2;

    @JsonProperty("pvp_div3")
    private DetailedStatsDto pvp_div3;

    @JsonProperty("pvp_solo")
    private DetailedStatsDto pvp_solo;

    @Override
    public int compareTo(ShipDataDto o) {
        return (int)(o.last_battle_time - this.last_battle_time);
    }
}
