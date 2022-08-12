package com.personal.diplom.Servise.util;

import com.personal.diplom.api.request.AddPostRequest;
import com.personal.diplom.api.response.ErrorResponse;
import com.personal.diplom.api.response.ResponseResult;

public class CheckData {

    public static ResponseResult CheckPost(AddPostRequest addPostRequest){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setResult(true);
        ErrorResponse errorResponse = new ErrorResponse();
        if (addPostRequest.getTitle() == null) {
            responseResult.setResult(false);
            errorResponse.setTitle("Отсутствует заголовок поста");
        }else  {
            if (addPostRequest.getTitle().length() < 3){
                responseResult.setResult(false);
                errorResponse.setTitle("Заголовок должен быть более 3 символов");
            }
        };
        if (addPostRequest.getText() == null) {
            responseResult.setResult(false);
            errorResponse.setText("Отсутствует текст поста");
        }else {
            if (addPostRequest.getText().length() < 50) {
                responseResult.setResult(false);
                errorResponse.setText("Текст поста должен быть более 50 символов");
            }
        }
        if  ((errorResponse.getTitle() != null )||(errorResponse.getText() != null)){
            responseResult.setErrorResponse(errorResponse);
            responseResult.setResult(false);
            return responseResult;
        }
        return responseResult;
    }
}
