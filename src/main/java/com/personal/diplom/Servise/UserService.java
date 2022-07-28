package com.personal.diplom.Servise;

import com.personal.diplom.api.response.UserCheckResponse;
import com.personal.diplom.api.response.UserPostResponse;
import com.personal.diplom.api.response.UserRegisterResponse;
import com.personal.diplom.model.CaptchaCode;
import com.personal.diplom.model.User;
import com.personal.diplom.repository.CaptchaRepository;
import com.personal.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CaptchaRepository captchaRepository;

    public long getUserId(String email){
        Optional<com.personal.diplom.model.User> currentUserO = userRepository.findByEmail(email);
        //.orElseThrow(
        //      () -> new UsernameNotFoundException(email)
        //);
        com.personal.diplom.model.User currentUser = currentUserO.get();
        return currentUser.getId();
    }

    public UserPostResponse getUserPostResponse(int id){

        Optional<User> optionalUser = userRepository.findById(id);
        User user = new User();
        user = optionalUser.get();
        UserPostResponse userPostResponse = new UserPostResponse();
        userPostResponse.setId(user.getId());
        userPostResponse.setName(user.getName());
        return userPostResponse;
    }
    private UserCheckResponse checkUserRegister(String name,String email, String password, String captha, String secret){
        UserCheckResponse userCheckResponse = new UserCheckResponse();

        System.out.println("name "+name);
        String inputTest = name.replaceAll("[A-zА-яа-я\\-]","").trim();

   //     input = name.replaceAll("\\s+"," ");
        System.out.println("inputTest "+inputTest);
        if (!inputTest.isEmpty()){
           userCheckResponse.setName("Имя введено неправильно");
        }

        if (email.indexOf("@") == 0 ){
            System.out.println("нет @");
            userCheckResponse.setEmail("e-mail введён неправильно");
        }else {
            Optional<User> listUser = userRepository.findByEmail(email);
         //   System.out.println("listUser.size() "+listUser.size());
            //    if (listUser.size() > 0) {
              //  userCheckResponse.setEmail("Этот e-mail уже зарегистрирован");
            //}
        }
        if(password.length() < 6 ){  userCheckResponse.setPassword("Пароль короче 6-ти символов");}
        List<CaptchaCode> listCaptha =  captchaRepository.findBySecretCode(secret);
        for (CaptchaCode item:listCaptha){
             if (!item.getCode().equals(captha)){
                userCheckResponse.setCaptcha("Код с картинки введён неверно");
                break;
            }
        }
        return userCheckResponse;
    }

    public UserRegisterResponse addUser(String email, String password, String name, String captcha, String secret)
    {
        User user = new User();
        UserCheckResponse userCheckResponse = new UserCheckResponse();
        userCheckResponse = checkUserRegister(name,email,password,captcha,secret);
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        System.out.println("addUser "+userCheckResponse.getEmail());
        if (userCheckResponse.getCaptcha().isEmpty()&
            userCheckResponse.getEmail().isEmpty()&
            userCheckResponse.getName().isEmpty()&
            userCheckResponse.getPassword().isEmpty()          )
            {
            System.out.println("addUser true");
           user.setName(name);
           user.setEmail(email);
           user.setPassword(password);
           user.setCode(secret);
           user.setIsModerator(0);
           user.setRegTime(new Date());
           userRepository.save(user);
           userRegisterResponse.setResult(true);
        }
        else {
            System.out.println("addUser false");
            userRegisterResponse.setResult(false);
            userRegisterResponse.setErrors(userCheckResponse);
        }
           return userRegisterResponse;
    }
}
