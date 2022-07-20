package com.personal.diplom.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    private Boolean result;
    @JsonProperty("user")
    private UserLoginResponse userLoginResponse;

}
