package com.personal.diplom.api.response;

import java.util.ArrayList;

public class CalendarYearResponse {
    private ArrayList<Integer> years = new ArrayList<>();
    private ArrayList<CalendarDataCountPost> posts = new ArrayList<>();

    public ArrayList<Integer> getYears() {
        return years;
    }

    public void setYears(ArrayList<Integer> years) {
        this.years = years;
    }

    public ArrayList<CalendarDataCountPost> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<CalendarDataCountPost> posts) {
        this.posts = posts;
    }
    public void addPost(CalendarDataCountPost calendarDataCountPost){
        posts.add(calendarDataCountPost);
    }
    public void addYear(Integer year){
        years.add(year);
    }
}
