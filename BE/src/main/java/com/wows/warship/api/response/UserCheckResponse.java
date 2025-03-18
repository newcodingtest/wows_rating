package com.wows.warship.api.response;

import com.wows.warship.dto.UserAccount;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCheckResponse {
    private Long userId;
    private String nickname;

    public static UserCheckResponse from(UserAccount userAccount){
        return UserCheckResponse.builder()
                .userId(userAccount.getId())
                .nickname(userAccount.getNickname())
                .build();
    }
}
