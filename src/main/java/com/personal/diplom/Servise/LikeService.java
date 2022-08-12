package com.personal.diplom.Servise;

import com.personal.diplom.api.response.ResponseResult;
import com.personal.diplom.model.Post;
import com.personal.diplom.model.PostVotes;
import com.personal.diplom.model.User;
import com.personal.diplom.repository.PostRepository;
import com.personal.diplom.repository.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    UserService userService;
    @Autowired
    VotesRepository votesRepository;
    @Autowired
    PostRepository postRepository;

    public ResponseResult addLike(Integer postId, Principal principal,int vote){
        ResponseResult responseResult = new ResponseResult();
        User user = userService.getUser(principal.getName());
        Post post = postRepository.findById(postId).get();
        PostVotes postVotes = votesRepository.findFirstByPostAndUser(post,user).orElse(new PostVotes());
        if (postVotes.getValue() != 0 ){
            if (postVotes.getValue() == vote ) {
                responseResult.setResult(false);
            } else {
                postVotes.setValue(postVotes.getValue() * (-1));
                responseResult.setResult(true);
            }

        }else {
            postVotes.setPost(post);
            postVotes.setUser(user);
            postVotes.setTime(new Date());
            postVotes.setValue(vote);
            responseResult.setResult(true);
        }
        votesRepository.save(postVotes);
        return responseResult;
    }
}
