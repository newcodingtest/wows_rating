package com.wows.warship.common.api.response;

import com.wows.warship.account.domain.UserAccount;
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
