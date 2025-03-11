package com.wows.warship.service;

import com.wows.warship.dto.UserAccount;
import com.wows.warship.entity.UserAccountEntity;
import com.wows.warship.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public void regist(UserAccount userAccount){
        isUserInDb(userAccount.getNickname());
        isUserExist(userAccount.getNickname());

        /**
         * 1.실제 계정이 존재하는지 db로 확인         *
         *  1-1. db에 존재하지 않았을때 api로 확인
         *   1-1-1. api에 존재한다면 db 저장
         *
         * */
        Optional<UserAccountEntity> user = userAccountRepository.findByNickname(userAccount.getNickname());
        if (user.isEmpty()){
            userAccountRepository.save(UserAccountEntity.from(userAccount));
        }
    }

    private void isUserExist(String nickname) {
    }

    private boolean isUserInDb(String nickname){
        Optional<UserAccountEntity> user = userAccountRepository.findByNickname(nickname);
        if (user.isEmpty()){
            return false;
        }
        return true;
    }



}
