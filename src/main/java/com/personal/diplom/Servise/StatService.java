package com.personal.diplom.Servise;

import com.personal.diplom.api.response.StatResponse;
import com.personal.diplom.model.User;
import com.personal.diplom.repository.GlobalSettingsRepository;
import com.personal.diplom.repository.PostRepository;
import com.personal.diplom.repository.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class StatService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private VotesRepository votesRepository;
    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    public StatResponse getStat(Principal principal ){
       // StatResponse statResponse = new StatResponse();

        //if (principal == null) {
         //   return getStatAll(null);
        //}else {
         //   User user = userService.getUser(principal.getName());
          //  if (user.getIsModerator() == 1) {
               // return getStatAll(null);
           // }
          /*  statResponse.setPostsCount(postRepository.countPostUser(user));
            statResponse.setDislikesCount(votesRepository.countVotes(user, -1));
            statResponse.setLikesCount(votesRepository.countVotes(user, 1));
            Optional<Date> dateOptional = postRepository.firstPost(user);
            Date date;
            if (!dateOptional.isEmpty()) {
                date = dateOptional.get();
                statResponse.setFirstPublication((Long) getNormDate(date));
            }

            Optional<Integer> countOpt = postRepository.viewCount(user);
            if (countOpt.isEmpty() == false) {
                statResponse.setViewsCount(countOpt.get());
            }*/
      //  }
        return getStatAll(null);
    }
    public StatResponse getStatAll(User user ){
        StatResponse statResponse = new StatResponse();

        statResponse.setPostsCount(postRepository.countPostUser(user));
        statResponse.setDislikesCount(votesRepository.countVotes(user,-1));
        statResponse.setLikesCount(votesRepository.countVotes(user,1));
        Optional<Date> dateOptional = postRepository.firstPost(user);
        Date date ;
        if (dateOptional.isEmpty() == false){
            date = dateOptional.get();
            statResponse.setFirstPublication((Long)getNormDate(date));
        }

        Optional<Integer> countOpt = postRepository.viewCount(null);
        if (countOpt.isEmpty() == false) {
            statResponse.setViewsCount(countOpt.get());
        }
        return statResponse;
    }
    public Long getNormDate(Date date){
        LocalDate localD =  LocalDate.parse(date.toString().substring(0,10));
        LocalTime time = LocalTime.parse("00:00:00");
        ZoneOffset zone = ZoneOffset.of("Z");
        TimeZone.setDefault( TimeZone.getTimeZone("UTC"));
        return localD.toEpochSecond(time, zone);
    }
}
