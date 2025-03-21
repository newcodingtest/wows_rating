package com.wows.warship.account.api;

import com.wows.warship.common.api.response.UserCheckResponse;
import com.wows.warship.common.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserAccountApi {
    private final UserAccountService userAccountService;

    /**
     * 유저 존재 유무 확인
     * */
    @GetMapping("/check/{nickname}")
    public UserCheckResponse validUser(@PathVariable("nickname")String nickname){
        return UserCheckResponse
                .from(userAccountService.isUserExist(nickname));
    }
}
