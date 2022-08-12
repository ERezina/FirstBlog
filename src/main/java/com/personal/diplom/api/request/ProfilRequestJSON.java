package com.personal.diplom.api.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfilRequestJSON {
    private String name;
    private String email ;
    private String password;
    private Integer removePhoto;
}
