package com.personal.diplom.controller;

import com.personal.diplom.Servise.CaptchaService;
import com.personal.diplom.Servise.UserService;
import com.personal.diplom.api.request.PasswordRequest;
import com.personal.diplom.api.request.UserRegisterRequest;
import com.personal.diplom.api.response.*;
import com.personal.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@RestController
public class ApiAuthController {

    private final CaptchaService captchaService;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    @Value("${upload.path}")
    private String uploadPath;

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
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = getLoginResponse(user.getUsername());
        return ResponseEntity.ok(loginResponse);
    }

    @RequestMapping(value = "/api/auth/check" , method = RequestMethod.GET)
    public ResponseEntity<LoginResponse> check(Principal principal){
        if (principal == null){
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(getLoginResponse(principal.getName()));
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
    public ResponseEntity<ResponseResult> changePassword(@RequestBody PasswordRequest passwordRequest){

        return ResponseEntity.ok(captchaService.changePassword(passwordRequest));
    }

    @RequestMapping(value = "/api/auth/logout" , method = RequestMethod.GET)
    public ResponseEntity<ResponseResult> logout(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setResult(true);
        return ResponseEntity.ok(responseResult);
    }


    private LoginResponse getLoginResponse(String email){
        Optional<com.personal.diplom.model.User> currentUserO = userRepository.findByEmail(email);
        //.orElseThrow(
          //      () -> new UsernameNotFoundException(email)
        //);
        com.personal.diplom.model.User currentUser = currentUserO.get();
        System.out.println("currentUser "+currentUser.getName());
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setEmail(currentUser.getEmail());
        userLoginResponse.setName(currentUser.getName());
        userLoginResponse.setModeration(currentUser.getIsModerator() == 1 );
        userLoginResponse.setId(currentUser.getId());
        userLoginResponse.setPhoto(uploadPath + "/"+currentUser.getPhoto());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userLoginResponse);
        return loginResponse;
    }
}