package com.wows.warship.extract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
public class DetailedStatsDto {
    @JsonProperty("max_xp")
    int max_xp;

    @JsonProperty("updated_at")
    private long updated_at;
    @JsonProperty("last_battle_time")
    private long last_battle_time;
}
