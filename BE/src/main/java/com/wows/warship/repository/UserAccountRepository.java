package com.wows.warship.repository;

import com.wows.warship.dto.UserAccount;
import com.wows.warship.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByNickname(String nickname);
}
