package com.personal.diplom.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tags")
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "postTags")
    private Set<Post> postList = new HashSet<Post>() ;

    public Tag(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPostList(){

        return postList ;
    }
    public void addPost(Post post){
        postList.add(post);
    }

    public void removePost( Post post) {
        System.out.println("Удаляем в Тэгах");
        this.postList.remove(post);
        post.getPostTags().remove(this);
     }

  public  void delAllPost(){
        postList.removeAll(postList) ;
    }
}
