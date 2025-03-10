package com.wows.warship.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// https://api.worldofwarships.asia/wows/account/list/?application_id=4a5f774ca91614ec9e42bdb76474af15&search=noCap
// WorldOfWarshipsClient.java
@FeignClient(name = "worldOfWarshipsClient", url = "https://api.worldofwarships.asia/wows")
public interface UserAccountClient {

    @GetMapping("/account/list/")
    Map<String, Object> getAccountList(
            @RequestParam("application_id") String applicationId,
            @RequestParam("search") String search
    );
}


