package main.model;

import javax.persistence.*;

@Entity
@Table(name = "tag2post")
public class TagPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "post_id",nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Post post;

    @Column(name = "tag_id",nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Tag tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
