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
        Optional<UserAccountEntity> user = userAccountRepository.findByNickname(userAccount.getNickname());
        if (user.isEmpty()){
            userAccountRepository.save(UserAccountEntity.from(userAccount));
        }
    }



}
