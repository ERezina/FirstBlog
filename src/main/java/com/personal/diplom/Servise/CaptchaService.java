package com.personal.diplom.Servise;

import com.github.cage.Cage;
import com.github.cage.GCage;
import com.personal.diplom.api.request.PasswordRequest;
import com.personal.diplom.api.response.CaptchaResponse;
import com.personal.diplom.api.response.ErrorResponse;
import com.personal.diplom.api.response.ResponseResult;
import com.personal.diplom.model.CaptchaCode;
import com.personal.diplom.repository.CaptchaRepository;
import liquibase.pro.packaged.S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service
public class CaptchaService {

    @Autowired
    private CaptchaRepository captchaRepository;
    @Transactional
    public  CaptchaResponse createEncodeString() throws IOException {
        Cage cage = new GCage();
        OutputStream os = new FileOutputStream("captcha.jpg", false);
        String encodeString;
        CaptchaResponse captchaResponse = new CaptchaResponse();
        try {
            String capcode = cage.getTokenGenerator().next();
            String capcodeSecret = cage.getTokenGenerator().next();
            byte[] fileByte = cage.draw(capcode);
            encodeString = "data:image/png;base64, "+Base64.getEncoder().encodeToString(fileByte);

            CaptchaCode captchaCode = new CaptchaCode();
            captchaCode.setCode(capcode);
            captchaCode.setSecretCode(capcodeSecret);
            captchaCode.setTime(new Date());

            Calendar calendar = new GregorianCalendar();
            Date dat = calendar.getTime();
            calendar.add(Calendar.HOUR,-1);
            System.out.println("dat "+dat);
            System.out.println("dat -1 "+calendar.getTime());
            captchaRepository.deleteCaptchaCode(calendar.getTime());
            captchaRepository.save(captchaCode);

            captchaResponse.setImage(encodeString);
            captchaResponse.setSecret(captchaCode.getSecretCode());

            System.out.println("secret "+cage.getTokenGenerator().toString());
            System.out.println("image "+ encodeString);

        } finally {
            os.close();
        }
       return captchaResponse;
    }

    public ResponseResult changePassword(PasswordRequest passwordRequest){
        ResponseResult responseResult = new ResponseResult();
        ErrorResponse errorResponse = new ErrorResponse();
        if (passwordRequest.getPassword().length() < 6){
            responseResult.setResult(false);
            errorResponse.setPassword("Пароль короче 6-ти символов");
            responseResult.setErrorResponse(errorResponse);
            return responseResult;
        }
        Optional<CaptchaCode> captchaCodeOptional = Optional.of(
                captchaRepository.findFirstBySecretCode(passwordRequest.getCaptcha_secret())
                        .orElse(new CaptchaCode()));

        CaptchaCode captchaCode = captchaCodeOptional.get();
        if (captchaCode.getId() == 0) {
            errorResponse.setCode("\"Ссылка для восстановления пароля устарела.\n" +
                    " <a href=\n" +
                    " \\\"/auth/restore\\\">Запросить ссылку снова</a>");
            responseResult.setResult(false);
            responseResult.setErrorResponse(errorResponse);
            return responseResult;
        }
        if (captchaCode.getCode() == passwordRequest.getCaptcha()){
            responseResult.setResult(true);
        }else {
            errorResponse.setCaptcha("Код с картинки введён неверно");
            responseResult.setResult(false);
            responseResult.setErrorResponse(errorResponse);
        }
        return responseResult;
    }
}
