package com.personal.diplom.Servise;

import com.personal.diplom.api.request.PostModerationRequest;
import com.personal.diplom.api.response.ResponseResult;
import com.personal.diplom.model.ModerationStatusType;
import com.personal.diplom.model.Post;
import com.personal.diplom.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ModerationServise {
    @Autowired
    PostRepository postRepository;


    public ResponseResult setModerationStatus(PostModerationRequest postModerationRequest){
        ResponseResult responseResult = new ResponseResult();
        Optional<Post> postOpt = postRepository.findById(postModerationRequest.getPost_id());
        Post post = new Post();
        if (postOpt.isPresent()){
            post = postOpt.get();
            System.out.println(" postModerationRequest.getDecision() "+postModerationRequest.getDecision());
            if (postModerationRequest.getDecision().equals("accept")){
                post.setModerationStatus(ModerationStatusType.ACCEPTED);
            }else {
                post.setModerationStatus(ModerationStatusType.DECLINED);
            }
            postRepository.save(post);
            responseResult.setResult(true);
        }else { responseResult.setResult(false);}
        return responseResult;
    }
}
