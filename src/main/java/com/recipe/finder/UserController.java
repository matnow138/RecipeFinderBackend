package com.recipe.finder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.finder.entity.AuthRequest;
import com.recipe.finder.service.JwtService;
import com.recipe.finder.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("v1/auth")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserInfoService userService;

    @Autowired
    private JwtService jwtSerice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome this endpoint is not secure";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addNewUser")
    public String addNewUser(@RequestBody String userInfo) throws IOException {
        logger.info("passed user: {}",userInfo);
        return userService.addUser(userInfo);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile(){
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody String authRequest) throws JsonProcessingException {
        logger.info("trying to authorize: {}", authRequest);
        AuthRequest request = objectMapper.readValue(authRequest, AuthRequest.class);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated()){
            logger.info("Authorized {}", request.getUsername());
            return jwtSerice.generateToken(request.getUsername());
        }else{
            throw new UsernameNotFoundException("invalid user request");
        }
    }
}
