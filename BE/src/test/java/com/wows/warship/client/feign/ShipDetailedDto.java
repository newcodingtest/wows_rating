package com.wows.warship.client.feign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true) //정의되지 않은 포멧은 무시
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShipDetailedDto {
    @JsonProperty("tier")
    private String tier;
    @JsonProperty("name")
    private String name;
    @JsonProperty("default_profile")
    private DefaultProfile defaultProfile;  // 별도 DTO로 매핑

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class DefaultProfile {
        @JsonProperty("armour")
        private Armour armour;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Armour {
        @JsonProperty("health")
        private int health;
    }

}
