package com.personal.diplom.api.request;

import lombok.Data;

@Data
public class PasswordRequest {

    private String code;
    private String password;
    private String captcha;
    private String captcha_secret;

}
