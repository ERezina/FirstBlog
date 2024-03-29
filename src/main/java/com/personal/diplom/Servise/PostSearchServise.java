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
        Pageable elem = PageRequest.of(offset,limit);
        Page<Post> allProductsSortedByName;
        elem  = PageRequest.of(offset,limit,Sort.by("date").descending());
        allProductsSortedByName = postRepository.findSearchPostPagination(elem,query);

        ArrayList<PostResponse> postResponses = new ArrayList<PostResponse>();
        PostResponseWork postResponseWork = new PostResponseWork();
        for(Post post: allProductsSortedByName.getContent()){
           postResponses.add(postResponseWork.copyToPostResponse(post));
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

    public PostsCountResponse getPostsUser(int offset,int limit, String status, long idUser  ){
        PostsCountResponse postsCountResponse = new PostsCountResponse();
        try {
            postsCountResponse.addPost(getAllPostsUser(offset,limit,status,idUser));
            postsCountResponse.setCount(postsCountResponse.getPosts().size());
        }
        catch (Exception e){

        }

        return postsCountResponse;
    }

    private ArrayList<PostResponse> getAllPostsUser(int offset,int limit, String status, long idUser ){
        Pageable elem = PageRequest.of(offset,limit);
        Page<Post> allProductsSortedByName;
        elem  = PageRequest.of(offset,limit,Sort.by("date").descending());
        System.out.println("status "+status + " iduser "+idUser);
        try {
            allProductsSortedByName = postRepository.findSearchPostUserPagination(elem, status, idUser);
            ArrayList<PostResponse> postResponses = new ArrayList<PostResponse>();
            PostResponseWork postResponseWork = new PostResponseWork();
            for(Post post: allProductsSortedByName.getContent()){
                postResponses.add(postResponseWork.copyToPostResponse(post));
            }
            return postResponses;
        }catch(Exception e){
            System.out.println("dgsfghdkjfsdjkfdsk");
        }

return null;
    }
}
