package com.personal.diplom.api.request;

import lombok.Data;

@Data
public class PostModerationRequest {
    private Integer post_id;
    private String decision;
}
