package com.wows.warship.account.repository;

import com.wows.warship.account.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByNickname(String nickname);
    Optional<UserAccountEntity> findByAccountId(String accountId);
}
