package com.wows.warship.account.entity;

import com.wows.warship.common.entity.BaseTimeEntity;
import com.wows.warship.account.domain.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserAccountEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(name = "유저 아이디 번호")
    private String accountId;

    @Column(name = "유저 닉네임")
    private String nickname;

    @Column(name = "레이팅 점수")
    @ColumnDefault("0")
    private int ratingScore;


    public static UserAccountEntity from(UserAccount userAccount){
        return UserAccountEntity.builder()
                .accountId(userAccount.getAccountId())
                .nickname(userAccount.getNickname())
                .ratingScore(userAccount.getRatingScore())
                .nickname(userAccount.getNickname())
                .build();
    }

    public UserAccount toModel(){
        return UserAccount.builder()
                .accountId(accountId)
                .nickname(nickname)
                .ratingScore(ratingScore)
                .build();
    }
}
