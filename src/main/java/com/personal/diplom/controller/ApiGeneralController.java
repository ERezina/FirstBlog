package com.personal.diplom.controller;

import com.personal.diplom.Servise.*;
import com.personal.diplom.api.request.ProfilRequest;
import com.personal.diplom.api.request.ProfilRequestJSON;
import com.personal.diplom.api.response.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
public class ApiGeneralController {

    private final SettingsServise settingsServise;
    private final InitResponse initResponse;
    private final TagServise tagServise;
    private final CalendarServise calendarServise;
    private final StatService statService;
    private final UserService userService;


    public ApiGeneralController(SettingsServise settingsServise, InitResponse initResponse, TagServise tagServise, CalendarServise calendarServise, StatService statService, UserService userService){
        this.settingsServise = settingsServise;
        this.initResponse = initResponse;
        this.tagServise = tagServise;
        this.calendarServise = calendarServise;
        this.statService = statService;
        this.userService = userService;
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


    @RequestMapping(value = "/api/moderation", method = RequestMethod.POST)
    public int postModeration(){
        return 235;
    }

 /*   @RequestMapping(value = "/api/profile/my", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseResult> editProfile(
                                    @RequestBody ProfilRequest profilRequest,
                                    Principal principal) {
        return ResponseEntity.ok(userService.editProfile(profilRequest,principal));
    }*/
 @RequestMapping(value = "/api/profile/my", method = RequestMethod.POST,
         consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
 @PreAuthorize("hasAuthority('user:write')")
 public ResponseEntity<ResponseResult> editProfile(
         @ModelAttribute ProfilRequest profilRequest,
         Principal principal) throws IOException {
     return ResponseEntity.ok(userService.editProfile(profilRequest,principal));
 }

    @RequestMapping(value = "/api/profile/my", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseResult> editProfileJSON(
            @RequestBody ProfilRequestJSON profilRequestJSON,
            Principal principal) throws IOException {
        return ResponseEntity.ok(userService.editProfileJSON(profilRequestJSON,principal));
    }

    @RequestMapping(value = "/api/statistics/my", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<StatResponse> myStatistics(Principal principal){
        return ResponseEntity.ok(statService.getStat(principal));
    }

    @RequestMapping(value = "/api/statistics/all", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<StatResponse> allStatistics(Principal principal){

        return ResponseEntity.ok(statService.getStat(principal));
    }


    /*  @RequestMapping(value = "/api/statistics/all", method = RequestMethod.GET)
    public ResponseEntity<StatResponse> allStatisticsForAll(){
        return ResponseEntity.ok(statService.getStatAll());
    }*/

    @RequestMapping(value = "/api/settings", method = RequestMethod.PUT)
    public int saveSettings(){
        return 239;
    }

}