package com.wows.warship.dto;

import com.wows.warship.entity.UserAccountEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserAccount {
    private String accountId;
    private String nickname;
    private int ratingScore;
    private int wins;


}
