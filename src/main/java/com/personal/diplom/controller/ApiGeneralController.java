package com.personal.diplom.controller;

import com.personal.diplom.Servise.CalendarServise;
import com.personal.diplom.Servise.SettingsServise;
import com.personal.diplom.Servise.TagServise;
import com.personal.diplom.api.response.CalendarYearResponse;
import com.personal.diplom.api.response.InitResponse;
import com.personal.diplom.api.response.SettingsResponse;
import com.personal.diplom.api.response.TagsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {

    private final SettingsServise settingsServise;
    private final InitResponse initResponse;
    private final TagServise tagServise;
    private final CalendarServise calendarServise;

    public ApiGeneralController(SettingsServise settingsServise, InitResponse initResponse, TagServise tagServise, CalendarServise calendarServise){
        this.settingsServise = settingsServise;
        this.initResponse = initResponse;
        this.tagServise = tagServise;
        this.calendarServise = calendarServise;
    }

    @RequestMapping(value = "/api/init", method = RequestMethod.GET)
    public InitResponse init(){
        return initResponse;
    }

    @RequestMapping(value = "/api/settings", method = RequestMethod.GET)
    public ResponseEntity<SettingsResponse> getSettings(){
        return new ResponseEntity<>(settingsServise.getGlobalService(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/tag", method = RequestMethod.GET)
    public TagsResponse getListTag(@RequestParam(value = "query",required = false) String query  ){
        // public TagsResponse getListTag(){
        return tagServise.getTagsWeight(query);
    }

    @RequestMapping(value = "/api/calendar", method = RequestMethod.GET)
    public CalendarYearResponse getCalendar(@RequestParam(value = "year",required = false) Integer year){

        return calendarServise.getCalendarResponse(year);
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