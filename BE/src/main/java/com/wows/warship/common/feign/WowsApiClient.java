package com.wows.warship.common.feign;

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


    //https://api.worldofwarships.asia/wows/encyclopedia/ships/?application_id=4a5f774ca91614ec9e42bdb76474af15&language=en&ship_id=3542005744&fields=name%2C+tier%2C+default_profile.armour.health
    /**
     *  배 상세 정보 검색
     * 
     *  https://api.worldofwarships.asia/wows/encyclopedia/ships/?application_id=4a5f774ca91614ec9e42bdb76474af15&language=en&ship_id=4179506480&fields=name
     * */
    @GetMapping("/encyclopedia/ships/?fields=name%2C+tier%2C+default_profile.armour.health")
    Map<String, Object> getShipInfo(
            @RequestParam("application_id") String applicationId,
            @RequestParam("language") String language,
            @RequestParam("ship_id") String shipId
    );
    
    /**
     * 최근 유저 전적 검색
     *
     https://api.worldofwarships.asia/wows/ships/stats/?application_id=4a5f774ca91614ec9e42bdb76474af15&extra=pvp_solo%2Cpvp_div2%2Cpvp_div3%2Crank_solo&language=en&account_id=2020639284
     * */

    @GetMapping("/ships/stats/?language=en&extra=pvp_solo%2Cpvp_div2%2Cpvp_div3%2Crank_solo")
    Map<String, Object> getBattleHistory(
            @RequestParam("application_id") String applicationId,
            @RequestParam("account_id") String account_id
    );
}

