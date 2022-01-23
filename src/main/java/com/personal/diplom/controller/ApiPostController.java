package com.personal.diplom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {
    @RequestMapping(value = "/api/post", method = RequestMethod.POST)
    public int setPost(){
        return 4;
    }

    @RequestMapping(value = "/api/post/{id}", method = RequestMethod.PUT)
    public int editPost(){
        return 5;
    }

    @RequestMapping(value = "/api/post/search", method = RequestMethod.GET)
    public int postSearch(){
        return 41;
    }
    @RequestMapping(value = "/api/post/byDate", method = RequestMethod.GET)
    public int postSearchByDate(){
        return 42;
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