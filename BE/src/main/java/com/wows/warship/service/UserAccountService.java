package com.wows.warship.service;

import com.wows.warship.client.feign.WowsApiClient;
import com.wows.warship.dto.UserAccount;
import com.wows.warship.entity.UserAccountEntity;
import com.wows.warship.exception.WowsErrorCode;
import com.wows.warship.exception.WowsException;
import com.wows.warship.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserAccountService {

    @Value("${wows.api.key}")
    private String apiKey;

    private final WowsApiClient wowsApiClient;

    private final UserAccountRepository userAccountRepository;


    public List<UserAccount> getAccounts(){
        return userAccountRepository.findAll().stream().map(UserAccountEntity::toModel)
                .collect(Collectors.toList());
    }

    public UserAccount getRate(String nickname){
        return userAccountRepository
                .findByNickname(nickname).get().toModel();
    }

    public UserAccount isUserExist(String nickname){
        Optional<UserAccount> find = isUserExistInDb(nickname);

        if(find.isEmpty()){
           return isUserExistInApi(nickname);
        }
        return find.get();
    }

    private UserAccount isUserExistInApi(String nickname) {
        Map<String, Object> response = wowsApiClient.getAccountList(apiKey, nickname);

        ArrayList<Map<String, String>> actual = (ArrayList)response.get("data");

        for (Map<String, String> names : actual){
            String name = names.get("nickname");
            if (name.equals(nickname)){
                String id = String.valueOf(names.get("account_id"));
                UserAccount userAccount = UserAccount.builder()
                        .nickname(nickname)
                        .accountId(id)
                        .build();
                userAccountRepository.save(UserAccountEntity.from(userAccount));
                return userAccount;
            }
        }
        throw new WowsException.NotFoundUserException(WowsErrorCode.NOT_FOUNT_USER, nickname);
    }

    private Optional<UserAccount> isUserExistInDb(String nickname){
        Optional<UserAccountEntity> user = userAccountRepository.findByNickname(nickname);
        if (user.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(user.get().toModel());
    }
}
