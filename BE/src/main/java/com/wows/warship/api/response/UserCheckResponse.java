package com.wows.warship.api.response;

import com.wows.warship.dto.UserAccount;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCheckResponse {
    private String userId;
    private String nickname;

    public static UserCheckResponse from(UserAccount userAccount){
        return UserCheckResponse.builder()
                .userId(userAccount.getAccountId())
                .nickname(userAccount.getNickname())
                .build();
    }
}
