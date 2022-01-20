package com.personal.diplom.model;

//import main.ModerationStatusType;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_active", nullable = false)
    private int isActive;

    @Enumerated(EnumType.STRING)
    @Column(name ="moderation_status",nullable = false,columnDefinition = "enum('NEW','ACCEPTED','DECLINED')")
    private ModerationStatusType moderationStatus;

   // @Column(name="moderator_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(name = "view_count",nullable = false)
    private int viewCount;

    @OneToMany
    @JoinColumn(name="post_id")
    private List<PostComment> commentCollection;

    @OneToMany
    @JoinColumn(name="post_id")
    private List<PostVotes> postVotesCollection;

    @ManyToMany
    @JoinTable(
            name = "tag2post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> postTags;

//    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
  //  private Collection<TagPost> tagPostCollection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public ModerationStatusType getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatusType moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public User getModeratorId() {
        return moderator;
    }

    public void setModeratorId(User moderator) {
        this.moderator = moderator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}