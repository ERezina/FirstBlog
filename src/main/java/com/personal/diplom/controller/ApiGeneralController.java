package com.personal.diplom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {

    @RequestMapping(value = "/api/init", method = RequestMethod.GET)
    public int init(){
        return 23;
    }

    @RequestMapping(value = "/api/settings", method = RequestMethod.GET)
    public int settings(){
        return 230;
    }

    @RequestMapping(value = "/api/tag", method = RequestMethod.GET)
    public int getListTag(){
        return 231;
    }

    @RequestMapping(value = "/api/calendar", method = RequestMethod.GET)
    public int getCalendar(){
        return 232;
    }
    @RequestMapping(value = "/api/image", method = RequestMethod.POST)
    public int uploadImage(){
        return 233;
    }
    @RequestMapping(value = "/api/comment", method = RequestMethod.POST)
    public int uploadComment(){
        return 234;
    }

    @RequestMapping(value = "/api/moderation", method = RequestMethod.POST)
    public int postModeration(){
        return 235;
    }

    @RequestMapping(value = "/api/profile/my", method = RequestMethod.POST)
    public int editProfile(){
        return 236;
    }

    @RequestMapping(value = "/api/statistics/my", method = RequestMethod.GET)
    public int myStatistics(){
        return 237;
    }

    @RequestMapping(value = "/api/statistics/all", method = RequestMethod.GET)
    public int allStatistics(){
        return 238;
    }

    @RequestMapping(value = "/api/settings", method = RequestMethod.PUT)
    public int saveSettings(){
        return 239;
    }

}