package com.personal.diplom.api.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddPostRequest {
    long timestamp;
    int active;
    String title;
    List<String> tags;
    String text;
}
