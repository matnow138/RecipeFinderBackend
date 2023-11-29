package com.recipe.finder.mapper;

import com.recipe.finder.domain.UserInfoDto;
import com.recipe.finder.entity.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserInfoDto mapToUserInfoDto(UserInfo userInfo){
        return UserInfoDto.builder()
                .id(userInfo.getId())
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .password(userInfo.getPassword())
                .roles(userInfo.getRoles())
                .build();

    }
    public UserInfo mapToUserInfo(UserInfoDto userInfoDto){
        return UserInfo.builder()
                .id(userInfoDto.getId())
                .name(userInfoDto.getName())
                .email(userInfoDto.getEmail())
                .password(userInfoDto.getPassword())
                .roles(userInfoDto.getRoles())
                .build();
    }
}
