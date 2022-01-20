package com.personal.diplom.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="post_comments")
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name="parent")
    @ManyToOne(cascade = CascadeType.ALL)
    private PostComment parent;

  //  @JoinColumn(name="post_id",nullable = false)
 //   @ManyToOne(cascade = CascadeType.ALL)
 //   private Post post;

    @JoinColumn(name="user_id",nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PostComment getParent() {
        return parent;
    }

    public void setParentId(PostComment parent) {
        this.parent = parent;
    }

  //  public Post getPostId() {
 //       return post;
  //  }

 //   public void setPostId(Post post) {
 //       this.post = post;
  //  }

    public User getUser() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}