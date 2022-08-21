package com.personal.diplom.controller;

import com.personal.diplom.Servise.*;
import com.personal.diplom.api.request.PostModerationRequest;
import com.personal.diplom.api.request.ProfilRequest;
import com.personal.diplom.api.request.ProfilRequestJSON;
import com.personal.diplom.api.request.SettingsRequest;
import com.personal.diplom.api.response.*;
import com.personal.diplom.model.GlobalSettings;
import com.personal.diplom.model.User;
import com.personal.diplom.repository.GlobalSettingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final ModerationServise moderationServise;
    private final GlobalSettingsRepository globalSettingsRepository;

    public ApiGeneralController(SettingsServise settingsServise, InitResponse initResponse, TagServise tagServise, CalendarServise calendarServise, StatService statService, UserService userService, ModerationServise moderationServise, GlobalSettingsRepository globalSettingsRepository){
        this.settingsServise = settingsServise;
        this.initResponse = initResponse;
        this.tagServise = tagServise;
        this.calendarServise = calendarServise;
        this.statService = statService;
        this.userService = userService;
        this.moderationServise = moderationServise;
        this.globalSettingsRepository = globalSettingsRepository;
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
    public ResponseEntity<ResponseResult> postModeration
            (@RequestBody PostModerationRequest postModerationRequest){

        return ResponseEntity.ok(moderationServise.setModerationStatus(postModerationRequest));
    }


 @RequestMapping(value = "/api/profile/my", method = RequestMethod.POST,
         consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
 @PreAuthorize("hasAuthority('user:write')")
 public ResponseEntity<ResponseResult> editProfile(
         @ModelAttribute ProfilRequest profilRequest,
         HttpServletRequest request,
         Principal principal) throws IOException {
     return ResponseEntity.ok(userService.editProfile(profilRequest,request,principal));
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
        User user = userService.getUser(principal.getName());
        return ResponseEntity.ok(statService.getStatAll(user));
    }

    @RequestMapping(value = "/api/statistics/all", method = RequestMethod.GET)
    public ResponseEntity<StatResponse> allStatistics(Principal principal){
        GlobalSettings globalSettings = globalSettingsRepository.findByCode("STATISTICS_IS_PUBLIC");
        if ((principal == null)&(!globalSettings.getValue().equals("YES"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatResponse());
        }
        return ResponseEntity.ok(statService.getStat(principal));
    }

    @RequestMapping(value = "/api/settings", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseResult> saveSettings(@RequestBody SettingsResponse settingsResponse){

        return ResponseEntity.ok(settingsServise.setGlobalSettingsAll(settingsResponse));
    }

}