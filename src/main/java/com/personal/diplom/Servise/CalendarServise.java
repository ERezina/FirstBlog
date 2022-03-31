package com.personal.diplom.Servise;

import com.personal.diplom.api.response.CalendarDataCountPost;
import com.personal.diplom.api.response.CalendarYearResponse;
import com.personal.diplom.model.CountPostByDate;
import com.personal.diplom.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CalendarServise {
    @Autowired
    private CalendarRepository calendarRepository;

    public CalendarYearResponse getCalendarResponse(Integer year){
        CalendarYearResponse calendarYearResponse = new CalendarYearResponse();
        Collection<CountPostByDate>  countPostByDates = calendarRepository.GetCountPost(year);
        calendarYearResponse.addYear(year);
        for (CountPostByDate item : countPostByDates){
            CalendarDataCountPost calendarDataCountPost = new CalendarDataCountPost();
            calendarDataCountPost.setDate(item.getDate());
            calendarDataCountPost.setCountPost(item.getCountPost());
            calendarYearResponse.addPost(calendarDataCountPost);

        }
        return calendarYearResponse;
    }
}
