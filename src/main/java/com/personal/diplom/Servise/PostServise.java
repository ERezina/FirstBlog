package com.personal.diplom.Servise;

import com.personal.diplom.Servise.util.CheckData;
import com.personal.diplom.Servise.util.PostResponseWork;
import com.personal.diplom.api.request.AddPostRequest;
import com.personal.diplom.api.response.*;
import com.personal.diplom.model.*;
import com.personal.diplom.repository.PostRepository;
import com.personal.diplom.repository.TagRepository;
import com.personal.diplom.repository.UserRepository;
//import org.glassfish.jaxb.core.util.Which;
//import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServise {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;

    private ArrayList<PostResponse> getAllPosts(int offset,int limit, String mode ){
        if(mode == null){mode = "recent"; }
        Pageable elem = PageRequest.of(offset,limit);
        Page<Post> allProductsSortedByName;
        switch (mode) {
            case "best" : allProductsSortedByName = postRepository.findAllPostPaginationSortVotes(elem);
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

    public ResponseEntity getPostById(int id){
        Post post = new Post();
        System.out.println("in getPostById");
        Optional<Post> optionalPost = postRepository.findById(id);
        System.out.println("in getPostById optionalPost ");
        if (!optionalPost.isPresent()) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        post = optionalPost.get();
        System.out.println("post "+post.toString());
        PostIdResponse postIdResponse = new PostIdResponse();
        postIdResponse.setId(post.getId());
        postIdResponse.setActive(post.getIsActive()==1?true:false);
        postIdResponse.setUser(PostResponseWork.copyUserPostResponse(post.getUser()));

        postIdResponse.setTitle(post.getTitle());
        postIdResponse.setText(post.getText());

        postIdResponse.setLikeCount(post.getCountLike());
        postIdResponse.setDislikeCount(post.getCountDislike());
        postIdResponse.setViewCount(post.addViewCount());

        LocalDate localD =  LocalDate.parse(post.getDate().toString().substring(0,10));
        LocalTime time = LocalTime.parse("00:00:00");
        ZoneOffset zone = ZoneOffset.of("Z");
        Date datePost = post.getDate();
        TimeZone.setDefault( TimeZone.getTimeZone("UTC"));
        postIdResponse.setTimestamp(localD.toEpochSecond(time, zone));

        for (PostComment postComment:post.getCommentCollection()){
            CommentsResponse commentsResponse = new CommentsResponse();
            commentsResponse.setId(postComment.getId());
            LocalDate localDateComment =  LocalDate.parse(postComment.getTime().toString().substring(0,10));
            commentsResponse.setTimestamp(localDateComment.toEpochSecond(time,zone));

            commentsResponse.setText(postComment.getText());

            UserFotoResponse userFotoResponse = new UserFotoResponse();
            userFotoResponse.setId(postComment.getUser().getId());
            userFotoResponse.setName(postComment.getUser().getName());
            userFotoResponse.setFoto(postComment.getUser().getPhoto());
            commentsResponse.setUser(userFotoResponse);

            postIdResponse.addComments(commentsResponse);
        }

        for (Tag tag:post.getPostTags()){
           postIdResponse.addTags(tag.getName());
        }
        postRepository.save(post);
        return new ResponseEntity(postIdResponse,HttpStatus.OK);
    }

    private void addTags(Post post, List<String> tagSet){
        for (String items : tagSet) {
            System.out.println("addTags 1");
            Tag tag = tagRepository.findFirstByName(items).orElse(new Tag(items));
            tagRepository.save(tag);
            post.addTag(tag);
            tag.addPost(post);
            tagRepository.save(tag);
            postRepository.save(post);

        }
    }
    public  ResponseResult addPost(AddPostRequest addPostRequest, Principal principal){
        ResponseResult responseResult = CheckData.CheckPost(addPostRequest);
        if (responseResult.getResult()) {
            Post post = new Post();
            //   System.out.println( new Date( localD.toEpochSecond(time, zone)));
            Date nowDate = new Date();
            Date userDate = new Date((long) addPostRequest.getTimestamp() * 1000);
            if (nowDate.after(userDate)) {
                userDate = nowDate;
            }

            post.setDate(userDate);

            post.setTitle(addPostRequest.getTitle());
            post.setText(addPostRequest.getText());
            post.setModerationStatus(ModerationStatusType.NEW);
            post.setIsActive(addPostRequest.getActive());
            post.setViewCount(0);
            post.setUser(userService.getUser(principal.getName()));
            postRepository.save(post);

            addTags(post,addPostRequest.getTags());

          responseResult.setResult(true);
        }
        return responseResult;
    }


    public  ResponseResult editPost(int idPost,AddPostRequest addPostRequest, Principal principal){
        ResponseResult responseResult = CheckData.CheckPost(addPostRequest);
        System.out.println("responseResult.getResult() "+responseResult.getResult().toString());

        if (responseResult.getResult()) {
            System.out.println("result = true");
            Post post = postRepository.findById(idPost).orElseThrow(() -> new NullPointerException());
            //   System.out.println( new Date( localD.toEpochSecond(time, zone)));
            Date nowDate = new Date();
            Date userDate = new Date((long) addPostRequest.getTimestamp() * 1000);
            if (nowDate.after(userDate)) {
                userDate = nowDate;
            }
            post.setDate(userDate);
            post.setTitle(addPostRequest.getTitle());
            post.setText(addPostRequest.getText());
          //  post.setModerationStatus(ModerationStatusType.NEW);
            post.setIsActive(addPostRequest.getActive());
            User user = userService.getUser(principal.getName());
            if (post.getUser().getId() == user.getId()) {
                post.setModerationStatus(ModerationStatusType.NEW);
            }
            postRepository.save(post);

            List<String> tagList = post.getPostTags().stream().map( p -> p.getName()).collect(Collectors.toList());
            //удалим все теги
            for (String items : tagList) {
                Optional<Tag> optag = tagRepository.findFirstByName(items);
                if (optag.isPresent()){
                    Tag tag = optag.get();
                    tag.removePost(post);
                    tagRepository.save(tag);
                    postRepository.save(post);
                }

            }
            addTags(post,addPostRequest.getTags());
            responseResult.setResult(true);
        }
        return responseResult;
    }
    public PostsCountResponse getListModeration(int offset,int limit, String status ,Principal principal ){
        PostsCountResponse postsCountResponse = new PostsCountResponse();
        User user = userRepository.findByEmail(principal.getName()).get();

        try {
            postsCountResponse.addPost(getAllPosts(offset,limit,status,user));
            postsCountResponse.setCount(postsCountResponse.getPosts().size());
        }
        catch (Exception e){

        }

        return postsCountResponse;
    }

    private ArrayList<PostResponse> getAllPosts(int offset,int limit, String status,User user ){

        Pageable elem = PageRequest.of(offset,limit);
        Page<Post> allProductsSortedByName;
        System.out.println("Модератор "+user.getId());
        System.out.println("статус "+status.toUpperCase(Locale.ROOT));
        allProductsSortedByName = postRepository.findAllPostPaginationModerator(
                elem,
                status.toUpperCase(Locale.ROOT),
                user.getId()
        );


        ArrayList<PostResponse> postResponses = new ArrayList<PostResponse>();
        PostResponseWork postResponseWork = new PostResponseWork();
        for(Post post: allProductsSortedByName.getContent()){
            postResponses.add(postResponseWork.copyToPostResponse(post));
        }
        return postResponses;
    }

}
