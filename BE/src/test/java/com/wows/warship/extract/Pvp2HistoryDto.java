package com.wows.warship.extract;


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
public class Pvp2HistoryDto implements Comparable<Pvp2HistoryDto> {
    @JsonProperty("pvp_div2")
    private PvpStats pvp;
    @JsonProperty("updated_at")
    private long updated_at;
    @JsonProperty("last_battle_time")
    private long last_battle_time;
    @JsonProperty("ship_id")
    private long ship_id;

    @Override
    public int compareTo(Pvp2HistoryDto o) {
        return (int)(o.last_battle_time - this.last_battle_time);
    }
}
