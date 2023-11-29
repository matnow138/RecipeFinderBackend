package com.recipe.finder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.finder.domain.UserInfoDto;
import com.recipe.finder.entity.UserInfo;
import com.recipe.finder.entity.UserInfoDetails;
import com.recipe.finder.mapper.UserMapper;
import com.recipe.finder.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;




    private final UserMapper userMapper = new UserMapper();

    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(UserInfoService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByName(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(String userInfoDto) throws IOException {
        UserInfoDto mappedUserInfoDto = objectMapper.readValue(userInfoDto, UserInfoDto.class);
        logger.info("passed UserInfoDto: {}", userInfoDto);
        UserInfo userInfo = userMapper.mapToUserInfo(mappedUserInfoDto);
        logger.info("mapped password: {}", userInfo.getPassword());
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }




}
