package com.personal.diplom.api.response;

import java.util.Date;

public class CalendarDataCountPost {
    private Date date;
    private Double countPost;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getCountPost() {
        return countPost;
    }

    public void setCountPost(Double countPost) {
        this.countPost = countPost;
    }

}
