package com.wows.warship.service;

import com.wows.warship.client.feign.UserAccountClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class TestService {

    private final UserAccountClient userAccountClient;

    public Map<String, Object> getUserAccountInfo(String nickname) {
        String applicationId = "4a5f774ca91614ec9e42bdb76474af15";  // Application ID
        return userAccountClient.getAccountList(applicationId, nickname);
    }
}
