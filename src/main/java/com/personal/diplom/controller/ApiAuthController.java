package com.personal.diplom.controller;

import com.personal.diplom.Servise.CaptchaService;
import com.personal.diplom.Servise.UserService;
import com.personal.diplom.api.request.UserRegisterRequest;
import com.personal.diplom.api.response.*;
import com.personal.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ApiAuthController {

    private final CaptchaService captchaService;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public ApiAuthController(CaptchaService captchaService, UserService userService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.captchaService = captchaService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/api/auth/login" , method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        com.personal.diplom.model.User currentUser = userRepository.findByEmail(user.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException(user.getUsername())
        );
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setEmail(currentUser.getEmail());
        userLoginResponse.setModeration(currentUser.getIsModerator() == 1 );
        userLoginResponse.setId(currentUser.getId());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userLoginResponse);

        System.out.println(loginRequest.getEmail());
        System.out.println((loginRequest.getPassword()));
        return ResponseEntity.ok(new LoginResponse());
    }

    @RequestMapping(value = "/api/auth/check" , method = RequestMethod.GET)
    public int check(){
        return 1;
    }

    @RequestMapping(value = "/api/auth/captcha" , method = RequestMethod.GET)
    public CaptchaResponse captcha() throws IOException {
        return captchaService.createEncodeString();
    }

    @RequestMapping(value = "/api/auth/register" , method = RequestMethod.POST)
    public UserRegisterResponse register(@RequestBody UserRegisterRequest userRegisterRequest
                     ){

        return userService.addUser(userRegisterRequest.getE_mail(),
                                    userRegisterRequest.getPassword(),
                                    userRegisterRequest.getName(),
                                    userRegisterRequest.getCaptcha(),
                                    userRegisterRequest.getCaptcha_secret());
    }

    @RequestMapping(value = "/api/auth" , method = RequestMethod.GET)
    public int enter(){
        return 5;
    }

    @RequestMapping(value = "/api/auth/restore" , method = RequestMethod.POST)
    public int restorePassword(){
        return 6;
    }

    @RequestMapping(value = "/api/auth/password" , method = RequestMethod.POST)
    public int changePassword(){
        return 7;
    }

    @RequestMapping(value = "/api/auth/logout" , method = RequestMethod.GET)
    public int logout(){
        return 8;
    }

}