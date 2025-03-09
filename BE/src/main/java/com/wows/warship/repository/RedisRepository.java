package com.wows.warship.repository;

import com.wows.warship.entity.RedisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends JpaRepository<RedisEntity, String> {
}
