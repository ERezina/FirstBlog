package com.personal.diplom.Servise;

import com.personal.diplom.api.response.PostResponse;
import com.personal.diplom.api.response.PostsCountResponse;
import com.personal.diplom.api.response.UserPostResponse;
import com.personal.diplom.model.Post;
import com.personal.diplom.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServise {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    private ArrayList<PostResponse> getAllPosts(){

        Iterable<Post> postsIterable =postRepository.findAll();
        ArrayList<PostResponse> postResponses = new ArrayList<PostResponse>();
        PostResponse postResponse;
        UserPostResponse userPostResponse ;
        for(Post post: postsIterable){
           postResponse = new PostResponse();
            postResponse.setId(post.getId());
            postResponse.setTimestamp(post.getDate().getTime());
            userPostResponse = new UserPostResponse();
            userPostResponse.setName(post.getUser().getName());
            userPostResponse.setId(post.getUser().getId());
            postResponse.setUser(userPostResponse);
            postResponse.setTitle(post.getTitle());

            postResponse.setAnnounce((post.getText().length()<150)?post.getText().substring(0,post.getText().length()):post.getText().substring(0,150));
            postResponse.setLikeCount((int)post.getCountLike());
            postResponse.setDislikeCount((int)post.getCountDislike());
            postResponse.setCommentCount(post.getCountComment());
            postResponse.setViewCount(post.getViewCount());
            postResponses.add(postResponse);
        }
        return postResponses;
    }

    public PostsCountResponse getPostsCount(){
        PostsCountResponse postsCountResponse = new PostsCountResponse();
   //     postsCountResponse.setCount(3);
        try {
            postsCountResponse.addPost(getAllPosts());
            postsCountResponse.setCount(postsCountResponse.getPosts().size());
        }
        catch (Exception e){

        }

       return postsCountResponse;
    }
}
