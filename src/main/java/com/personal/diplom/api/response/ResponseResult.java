package com.personal.diplom.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {
    private Boolean result;
    private ErrorResponse errorResponse;
    private int id;
}
