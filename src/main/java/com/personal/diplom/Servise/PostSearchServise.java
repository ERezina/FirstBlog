package com.personal.diplom.Servise;

import com.personal.diplom.api.response.PostResponse;
import com.personal.diplom.api.response.PostsCountResponse;
import com.personal.diplom.api.response.UserPostResponse;
import com.personal.diplom.model.Post;
import com.personal.diplom.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

@Service
public class PostSearchServise {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    private ArrayList<PostResponse> getAllPosts(int offset,int limit, String query ){
       // if(mode == null){mode = "recent"; }

      //  Pageable elem = PageRequest.of(offset,limit,Sort.by("date").descending());

        Pageable elem = PageRequest.of(offset,limit);
        Page<Post> allProductsSortedByName;
        elem  = PageRequest.of(offset,limit,Sort.by("date").descending());
        allProductsSortedByName = postRepository.findSearchPostPagination(elem,query);

        ArrayList<PostResponse> postResponses = new ArrayList<PostResponse>();
        PostResponse postResponse;
        UserPostResponse userPostResponse ;
        for(Post post: allProductsSortedByName.getContent()){
           postResponse = new PostResponse();
            postResponse.setId(post.getId());
            System.out.println("Дата в строке "+post.getDate().toString().substring(0,10));
            LocalDate localD =  LocalDate.parse(post.getDate().toString().substring(0,10));
            LocalTime time = LocalTime.parse("00:00:00");
            ZoneOffset zone = ZoneOffset.of("Z");
            Date datePost = post.getDate();
            TimeZone.setDefault( TimeZone.getTimeZone("UTC"));
            postResponse.setTimestamp(localD.toEpochSecond(time, zone));
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

    public PostsCountResponse getPostsCount(int offset,int limit, String query  ){
        PostsCountResponse postsCountResponse = new PostsCountResponse();
        try {
            postsCountResponse.addPost(getAllPosts(offset,limit,query));
            postsCountResponse.setCount(postsCountResponse.getPosts().size());
        }
        catch (Exception e){

        }

       return postsCountResponse;
    }


}
