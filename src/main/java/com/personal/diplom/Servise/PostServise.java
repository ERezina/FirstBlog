package com.personal.diplom.Servise;

import com.personal.diplom.Servise.util.PostResponseWork;
import com.personal.diplom.api.response.PostResponse;
import com.personal.diplom.api.response.PostsCountResponse;
import com.personal.diplom.api.response.UserPostResponse;
import com.personal.diplom.model.Post;
import com.personal.diplom.repository.PostRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class PostServise {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    private ArrayList<PostResponse> getAllPosts(int offset,int limit, String mode ){
        if(mode == null){mode = "recent"; }
        Pageable elem = PageRequest.of(offset,limit);
        Page<Post> allProductsSortedByName;
        switch (mode) {
            case "best" :  allProductsSortedByName = postRepository.findAllPostPaginationSortVotes(elem);
                        break;
            case "popular"  : allProductsSortedByName = postRepository.findAllPostPaginationSortComment(elem);
                                break;
            case "early": elem                      = PageRequest.of(offset,limit,Sort.by("date").ascending());
                          allProductsSortedByName   = postRepository.findAllPostPagination(elem);
                            break;
            default :     elem                      = PageRequest.of(offset,limit,Sort.by("date").descending());
                            allProductsSortedByName = postRepository.findAllPostPagination(elem);
            break;
        }

        ArrayList<PostResponse> postResponses = new ArrayList<PostResponse>();
        PostResponseWork postResponseWork = new PostResponseWork();
        for(Post post: allProductsSortedByName.getContent()){
            postResponses.add(postResponseWork.copyToPostResponse(post));
        }
        return postResponses;
    }

    public PostsCountResponse getPostsCount(int offset,int limit, String mode  ){
        PostsCountResponse postsCountResponse = new PostsCountResponse();
        try {
            postsCountResponse.addPost(getAllPosts(offset,limit,mode));
            postsCountResponse.setCount(postsCountResponse.getPosts().size());
        }
        catch (Exception e){

        }

       return postsCountResponse;
    }


}
