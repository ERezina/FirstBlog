package com.personal.diplom.Servise.util;

import com.personal.diplom.api.response.PostResponse;
import com.personal.diplom.api.response.UserPostResponse;
import com.personal.diplom.model.Post;
import com.personal.diplom.model.User;
import org.jsoup.Jsoup;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

public class PostResponseWork {

    public PostResponse copyToPostResponse(Post post){
        PostResponse postResponse;
        UserPostResponse userPostResponse ;
        String annonse;
        postResponse = new PostResponse();
        postResponse.setId(post.getId());
        LocalDate localD =  LocalDate.parse(post.getDate().toString().substring(0,10));
        LocalTime time = LocalTime.parse("00:00:00");
        ZoneOffset zone = ZoneOffset.of("Z");
        Date datePost = post.getDate();
        TimeZone.setDefault( TimeZone.getTimeZone("UTC"));
        postResponse.setTimestamp(localD.toEpochSecond(time, zone));
        userPostResponse = new UserPostResponse();
        userPostResponse.setName(post.getUser().getName());
        userPostResponse.setId(post.getUser().getId());
        postResponse.setUser(userPostResponse);
        postResponse.setTitle(post.getTitle());
        annonse = post.getText();
        annonse = Jsoup.parse(annonse).text();
        postResponse.setAnnounce((annonse.length()<150)?annonse.substring(0,annonse.length()):annonse.substring(0,150));
        postResponse.setLikeCount((int)post.getCountLike());
        postResponse.setDislikeCount((int)post.getCountDislike());
        postResponse.setCommentCount(post.getCountComment());
        postResponse.setViewCount(post.getViewCount());
        return postResponse;
    }

    public static UserPostResponse copyUserPostResponse(User user){
        UserPostResponse userPostResponse = new UserPostResponse();
        userPostResponse.setId(user.getId());
        userPostResponse.setName(user.getName());
        return userPostResponse;
    }

}
