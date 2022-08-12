package com.personal.diplom.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    String title;
    String text;
    String password;
    String code;
    String captcha;
}
