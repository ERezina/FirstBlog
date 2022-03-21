package com.personal.diplom.api.response;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;


public class TagsResponse {
    private ArrayList<TagsWeightResponse> tags = new ArrayList<>();

    public ArrayList<TagsWeightResponse> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagsWeightResponse> tags) {
        this.tags = tags;
    }
    public void addTag(TagsWeightResponse tagsWeightResponse){
        tags.add(tagsWeightResponse);
    }
}
