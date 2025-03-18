package com.wows.warship.entity;

import com.wows.warship.dto.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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
    private Long id;

    @Column(name = "유저 닉네임")
    private String nickname;

    @Column(name = "레이팅 점수")
    private int ratingScore;


    public static UserAccountEntity from(UserAccount userAccount){
        return UserAccountEntity.builder()
                .id(userAccount.getId())
                .nickname(userAccount.getNickname())
                .ratingScore(userAccount.getRatingScore())
                .nickname(userAccount.getNickname())
                .build();
    }

    public UserAccount toModel(){
        return UserAccount.builder()
                .nickname(nickname)
                .ratingScore(ratingScore)
                .build();
    }
}
