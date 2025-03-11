package com.wows.warship.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// WorldOfWarshipsClient.java
@FeignClient(name = "WowsApiClient", url = "https://api.worldofwarships.asia/wows")
public interface WowsApiClient {

    /**
     *  https://api.worldofwarships.asia/wows/account/list/?application_id=4a5f774ca91614ec9e42bdb76474af15&search=noCap
     * */
    @GetMapping("/account/list/")
    Map<String, Object>  getAccountList(
            @RequestParam("application_id") String applicationId,
            @RequestParam("search") String search
    );

    /**
     *  https://api.worldofwarships.asia/wows/encyclopedia/ships/?application_id=4a5f774ca91614ec9e42bdb76474af15&language=en&ship_id=4179506480&fields=name
     * */
    @GetMapping("/encyclopedia/ships/?fields=name")
    Map<String, Object> getShipInfo(
            @RequestParam("application_id") String applicationId,
            @RequestParam("language") String language,
            @RequestParam("ship_id") String shipId
    );
}

