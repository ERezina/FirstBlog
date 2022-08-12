package com.personal.diplom.api.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

@Data
public class ProfilRequest {
    private String name;
    private String email ;
    private String password;
    private MultipartFile photo;
    private Integer removePhoto;

}
