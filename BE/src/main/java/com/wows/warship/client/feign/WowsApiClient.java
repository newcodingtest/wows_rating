package com.wows.warship.client.feign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// WorldOfWarshipsClient.java
@FeignClient(name = "WowsApiClient", url = "https://api.worldofwarships.asia/wows")
public interface WowsApiClient {
    
    /**
     *  유저 닉네임 찾기
     *  
     *  https://api.worldofwarships.asia/wows/account/list/?application_id=4a5f774ca91614ec9e42bdb76474af15&search=noCap
     * */
    @GetMapping("/account/list/")
    Map<String, Object>  getAccountList(
            @RequestParam("application_id") String applicationId,
            @RequestParam("search") String nickname
    );

    
    
    /**
     *  배 상세 정보 검색
     * 
     *  https://api.worldofwarships.asia/wows/encyclopedia/ships/?application_id=4a5f774ca91614ec9e42bdb76474af15&language=en&ship_id=4179506480&fields=name
     * */
    @GetMapping("/encyclopedia/ships/?fields=name")
    Map<String, Object> getShipInfo(
            @RequestParam("application_id") String applicationId,
            @RequestParam("language") String language,
            @RequestParam("ship_id") String shipId
    );
    
    /**
     * 최근 유저 전적 검색
     * 
     * https://api.worldofwarships.asia/wows/ships/stats/?application_id=4a5f774ca91614ec9e42bdb76474af15&fields=pvp.capture_points%
     * 2Cpvp.team_capture_points%2Cpvp.max_damage_scouting%2Cpvp.damage_scouting%2Cpvp.max_total_agro%2Cpvp.frags%2Cpvp.battles%
     * 2Cpvp.max_damage_dealt%2Cpvp.wins%2Cpvp.losses%2Cpvp_div2.capture_points%2Cpvp_div2.team_capture_points%
     * 2Cpvp_div2.max_damage_scouting%2Cpvp_div2.damage_scouting%2Cpvp_div2.max_total_agro%2Cpvp_div2.frags%
     * 2Cpvp_div2.battles%2Cpvp_div2.max_damage_dealt%2Cpvp_div2.wins%2Cpvp_div2.losses%2Cship_id%2Cupdated_at%
     * 2Clast_battle_time&account_id=2020639284&language=en
     * */

    @GetMapping("/ships/stats/?language=en&fields=pvp.capture_points%2Cpvp.team_capture_points%2Cpvp.max_damage_scouting%" +
            "2Cpvp.damage_scouting%2Cpvp.max_total_agro%2Cpvp.frags%2Cpvp.battles%2Cpvp.max_damage_dealt%2Cpvp.wins%" +
            "2Cpvp.losses%2Cpvp_div2.capture_points%2Cpvp_div2.team_capture_points%2Cpvp_div2.max_damage_scouting%" +
            "2Cpvp_div2.damage_scouting%2Cpvp_div2.max_total_agro%2Cpvp_div2.frags%2Cpvp_div2.battles%" +
            "2Cpvp_div2.max_damage_dealt%2Cpvp_div2.wins%2Cpvp_div2.losses%2Cship_id%2Cupdated_at%2Clast_battle_time")
    Map<String, Object> getBattleHistory(
            @RequestParam("application_id") String applicationId,
            @RequestParam("account_id") String account_id
    );
}

