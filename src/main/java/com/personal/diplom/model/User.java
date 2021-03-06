package com.personal.diplom.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "is_moderator", nullable = false)
    private int isModerator;
    @Column(name="reg_time", nullable = false)
    private Date regTime;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email ;
    @Column(nullable = false)
    private String password;
    private String code;
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(int isModerator) {
        this.isModerator = isModerator;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Role getRole() {
        return isModerator == 1 ? Role.MODERATOR : Role.USER;
    }
}
