package com.personal.diplom.api.response;

public class CommentsResponse {
    private int id;
    private long timestamp;
    private String text;
    private UserFotoResponse user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserFotoResponse getUser() {
        return user;
    }

    public void setUser(UserFotoResponse user) {
        this.user = user;
    }
}
