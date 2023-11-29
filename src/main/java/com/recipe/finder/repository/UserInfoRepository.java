package com.recipe.finder.repository;

import com.recipe.finder.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);
}
