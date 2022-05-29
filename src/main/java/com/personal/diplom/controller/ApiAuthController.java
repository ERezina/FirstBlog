package com.personal.diplom.controller;

import com.personal.diplom.Servise.CaptchaService;
import com.personal.diplom.Servise.UserService;
import com.personal.diplom.api.request.UserRegisterRequest;
import com.personal.diplom.api.response.CaptchaResponse;
import com.personal.diplom.api.response.UserRegisterResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ApiAuthController {

    private final CaptchaService captchaService;
    private final UserService userService;
    public ApiAuthController(CaptchaService captchaService, UserService userService) {
        this.captchaService = captchaService;
        this.userService = userService;
    }

    @RequestMapping(value = "/api/auth" , method = RequestMethod.GET)
    public boolean add(){
        return false;
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

    @RequestMapping(value = "/api/auth/login" , method = RequestMethod.POST)
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