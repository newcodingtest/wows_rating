package com.wows.warship.account.repository;

import com.wows.warship.account.entity.UserAccountEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class UserAccountRepositoryTests {

    @Autowired
    private UserAccountRepository userAccountRepository;
    
    @DisplayName("accountId로 유저정보 찾기")
    @Test
    void findAccountId(){
        UserAccountEntity user = UserAccountEntity.builder()
                .accountId("1")
                .ratingScore(100)
                .build();
        
        userAccountRepository.save(user);

        UserAccountEntity find = userAccountRepository.findByAccountId(user.getAccountId())
                .get();

        assertTrue(find.getAccountId().equals(user.getAccountId()));
    }

}
