package com.wows.warship.account.domain;

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
