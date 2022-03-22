package com.personal.diplom.model;

import javax.persistence.*;
import java.util.Date;

//@Entity
//@Table(name="captcha_codes")
public class CaptchaCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    private String code;

    @Column(name="secret_code",nullable = false)
    private String secretCode;
}

