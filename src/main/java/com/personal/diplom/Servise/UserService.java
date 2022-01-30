package com.personal.diplom.Servise;

import com.personal.diplom.api.response.UserPostResponse;
import com.personal.diplom.model.User;
import com.personal.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserPostResponse getUserPostResponse(int id){

        Optional<User> optionalUser = userRepository.findById(id);
        User user = new User();
        user = optionalUser.get();
        UserPostResponse userPostResponse = new UserPostResponse();
        userPostResponse.setId(user.getId());
        userPostResponse.setName(user.getName());
        return userPostResponse;
    }
}
