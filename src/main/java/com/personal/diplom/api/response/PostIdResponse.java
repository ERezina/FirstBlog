package com.personal.diplom.api.response;

import java.util.ArrayList;

public class PostIdResponse {
    private int id;
    private long timestamp;
    private boolean active;
    private UserPostResponse user;

    private String title;
    private String text;
    private long likeCount;
    private long dislikeCount;
    private long viewCount;

    private ArrayList<CommentsResponse> comments = new ArrayList<>();

    private ArrayList<String> tags = new ArrayList<>();

    public  void addComments(CommentsResponse commentsResponse){
        this.comments.add(commentsResponse);
    }

    public void addTags(String tag){
        this.tags.add(tag);
    }
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserPostResponse getUser() {
        return user;
    }

    public void setUser(UserPostResponse user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public ArrayList<CommentsResponse> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentsResponse> comments) {
        this.comments = comments;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
