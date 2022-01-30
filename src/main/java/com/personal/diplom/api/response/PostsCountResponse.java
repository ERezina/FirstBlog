package com.personal.diplom.api.response;

import java.awt.*;
import java.util.ArrayList;

public class PostsCountResponse {
    private int count;
    private ArrayList<PostResponse> posts = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<PostResponse> getPosts() {
        return posts;
    }

    public void addPost(ArrayList<PostResponse> postList){
        posts.addAll(postList);
    }
    public void setPosts(ArrayList<PostResponse> posts) {
        this.posts = posts;
    }

}
