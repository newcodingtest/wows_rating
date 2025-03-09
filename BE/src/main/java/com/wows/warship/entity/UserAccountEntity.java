package com.wows.warship.entity;

import com.wows.warship.dto.UserAccount;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserAccountEntity extends BaseTimeEntity {

    @Id
    private Long seq;

    private String nickname;


    public static UserAccountEntity from(UserAccount userAccount){
        return UserAccountEntity.builder()
                .nickname(userAccount.getNickname())
                .build();
    }
}
