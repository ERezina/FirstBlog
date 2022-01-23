package com.personal.diplom.controller;

import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {

    @RequestMapping(value = "/api/auth" , method = RequestMethod.GET)
    public int add(){
        return 1;
    }

    @RequestMapping(value = "/api/auth/check" , method = RequestMethod.GET)
    public int check(){
        return 1;
    }

    @RequestMapping(value = "/api/auth/captcha" , method = RequestMethod.GET)
    public int captcha(){
        return 3;
    }

    @RequestMapping(value = "/api/auth/register" , method = RequestMethod.POST)
    public int register(){
        return 4;
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