package com.personal.diplom.api.request;

import lombok.Data;

@Data
public class CommentRequest {
    private Integer parent_id;
    private Integer post_id;
    private String text;
}
