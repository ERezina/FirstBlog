package com.personal.diplom.api.response;

public class UserRegisterResponse {
    private boolean result;
    private UserCheckResponse errors;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public UserCheckResponse getErrors() {
        return errors;
    }

    public void setErrors(UserCheckResponse errors) {
        this.errors = errors;
    }
}
