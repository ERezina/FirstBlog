package com.personal.diplom.api.response;

public class UserCheckResponse {
    private String email ;
    private String name;
    private String password;
    private String captcha;

    public String getEmail() {
        if (email == null){return "";}
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        if (name == null){return "";}
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        if (password == null){return "";}
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        if (captcha == null){return "";}
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
