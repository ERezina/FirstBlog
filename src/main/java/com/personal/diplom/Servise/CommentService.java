package com.personal.diplom.Servise;

import com.personal.diplom.api.request.CommentRequest;
import com.personal.diplom.api.response.ErrorResponse;
import com.personal.diplom.api.response.ResponseResult;
import com.personal.diplom.model.Post;
import com.personal.diplom.model.PostComment;
import com.personal.diplom.repository.CommentRepository;
import com.personal.diplom.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserService userService;

    public ResponseResult addComment(CommentRequest commentRequest, Principal principal){
        ResponseResult responseResult = new ResponseResult();
        ErrorResponse errorResponse = new ErrorResponse();
        PostComment postComment = new PostComment();
        Post post = new Post();
        PostComment parentComment;
        if (commentRequest.getParent_id() != null){
            Optional<PostComment> parentCommentOpt = commentRepository.findById(commentRequest.getParent_id())
                    ;
            if (parentCommentOpt.isPresent()){
                parentComment = parentCommentOpt.get();
                postComment.setParentId(parentComment);}
            else{
                errorResponse.setText("Не найден родительский комментарий с id = "+commentRequest.getParent_id());
            }
        };
        if(commentRequest.getPost_id() != null ){
            Optional<Post> postOpt =  postRepository.findById(commentRequest.getPost_id());
            if (postOpt.isPresent()){
                post = postOpt.get();
            //    postComment.setPost(post);
            }
            else{
                errorResponse.setText("Не найден пост с id = "+commentRequest.getPost_id());
            }

        }else{
            errorResponse.setText("Не указан пост ");
        };

        if ((commentRequest.getText() == null )||(commentRequest.getText().length() < 3)){
            errorResponse.setText("Текст комментария не задан или слишком короткий");
            responseResult.setResult(false);
            responseResult.setErrorResponse(errorResponse);

        }else{
            postComment.setText(commentRequest.getText());
            postComment.setTime(new Date());
            postComment.setUserId(userService.getUser(principal.getName()));
            postComment.setPost(post);
            commentRepository.save(postComment);
            post.addComment(postComment);

            postRepository.save(post);
            responseResult.setId(postComment.getId());
        }

        return responseResult;
    }
}
