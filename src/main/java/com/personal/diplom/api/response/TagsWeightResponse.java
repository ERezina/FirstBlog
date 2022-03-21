package com.personal.diplom.api.response;

import com.personal.diplom.repository.TagRepository;

public class TagsWeightResponse  {
    private String name;
    private Double weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
