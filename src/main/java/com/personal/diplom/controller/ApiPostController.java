package com.personal.diplom.controller;

import com.personal.diplom.Servise.PostSearchByDateServise;
import com.personal.diplom.Servise.PostSearchServise;
import com.personal.diplom.Servise.PostServise;
import com.personal.diplom.api.response.PostsCountResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {

    private final PostServise postServise;
    private final PostSearchServise postSearchServise;
    private final PostSearchByDateServise postSearchByDateServise;

    public ApiPostController(PostServise postServise, PostSearchServise postSearchServise, PostSearchByDateServise postSearchByDateServise) {
        this.postServise = postServise;
        this.postSearchServise = postSearchServise;
        this.postSearchByDateServise = postSearchByDateServise;
    }

    @RequestMapping(value = "/api/post", method = RequestMethod.GET)
    public PostsCountResponse setPost(@RequestParam("offset") int offset,@RequestParam("limit") int limit, @RequestParam("mode") String mode){
    //    PostsCountResponse postsCountResponse = new PostsCountResponse();
        return postServise.getPostsCount(offset,limit, mode);
    }

    @RequestMapping(value = "/api/post/search", method = RequestMethod.GET)
    public PostsCountResponse postSearch(@RequestParam("offset") int offset,@RequestParam("limit") int limit, @RequestParam(value = "query",required = false) String query){
        return postSearchServise.getPostsCount(offset,limit,query);
    }


    @RequestMapping(value = "/api/post/{id}", method = RequestMethod.PUT)
    public int editPost(){
        return 5;
    }



    @RequestMapping(value = "/api/post/byDate", method = RequestMethod.GET)
    public PostsCountResponse postSearchByDate(@RequestParam("offset") int offset,@RequestParam("limit") int limit, @RequestParam(value = "date",required = false) String query){
        return postSearchByDateServise.getPostsCount(offset,limit,query);
    }

    @RequestMapping(value = "/api/post/byTag", method = RequestMethod.GET)
    public int postSearchByTag(){
        return 43;
    }

    @RequestMapping(value = "/api/post/{ID}", method = RequestMethod.GET)
    public int postSearchByID(){
        return 44;
    }

    @RequestMapping(value = "/api/post/moderation", method = RequestMethod.GET)
    public int postListModeration(){
        return 45;
    }

    @RequestMapping(value = "/api/post/my", method = RequestMethod.GET)
    public int postListMy(){
        return 46;
    }

    @RequestMapping(value = "/api/post/like", method = RequestMethod.POST)
    public int postLike(){
        return 47;
    }

    @RequestMapping(value = "/api/post/dislike", method = RequestMethod.POST)
    public int postDislike(){
        return 48;
    }

}