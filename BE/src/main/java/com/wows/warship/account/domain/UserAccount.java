package com.wows.warship.account.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class UserAccount {
    private String accountId;
    private String nickname;
    private int ratingScore;
    private int wins;

    public boolean hasBattleRecord(){
        if (ratingScore==0){
            return false;
        }
        return true;
    }


}
