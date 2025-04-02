package com.wows.warship.account.service;

import com.wows.warship.common.feign.WowsApiClient;
import com.wows.warship.account.domain.UserAccount;
import com.wows.warship.account.entity.UserAccountEntity;
import com.wows.warship.common.exception.WowsErrorCode;
import com.wows.warship.common.exception.WowsException;
import com.wows.warship.account.repository.UserAccountRepository;
import com.wows.warship.common.service.WowsApiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAccountService {


    private final WowsApiService wowsApiService;

    private final UserAccountRepository userAccountRepository;

    @Transactional
    public void uppateRate(String accountId, int rating){
        log.info("update catch");
        UserAccountEntity account = userAccountRepository.findByAccountId(accountId).get();

        account.changeRatingScore(rating);
    }

    public List<UserAccount> getAccounts(){
        return userAccountRepository.findAll().stream().map(UserAccountEntity::toModel)
                .collect(Collectors.toList());
    }

    public UserAccount getRate(String nickname){
        return userAccountRepository
                .findByNickname(nickname.toLowerCase()).get().toModel();
    }

    public UserAccount isUserExist(String nickname){
        String lowerNickname = nickname.toLowerCase();
        Optional<UserAccount> find = isUserExistInDb(lowerNickname);

        if(find.isEmpty()){
           return isUserExistInApi(lowerNickname);
        }
        return find.get();
    }

    private UserAccount isUserExistInApi(String nickname) {
        Map<String, Object> response = wowsApiService.getUserAccountInfo(nickname);

        ArrayList<Map<String, String>> actual = (ArrayList)response.get("data");

        for (Map<String, String> names : actual){
            log.info("{}", names);
            String name = names.get("nickname").toLowerCase();
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
