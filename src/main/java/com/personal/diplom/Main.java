package com.personal.diplom;


import main.model.Book;
import main.model.Post;
import main.model.User;
import org.apache.catalina.core.StandardService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Main {


public static void main(String[] args) {

    SpringApplication.run(Main.class,args);
    StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
    SessionFactory sessionFactory= metadata.getSessionFactoryBuilder().build();

    Session session = sessionFactory.openSession();
    User user = new User();
    user.setCode("11");
    user.setEmail("ddd@lll");
    user.setIsModerator(1);
    user.setName("name");
    user.setPassword("password");
    user.setRegTime(new Date());

    session.save(user);

    Post post = new Post();
    post.setDate(new Date());
    post.setIsActive(0);
    post.setModeratorId(user);
    post.setText("текст текст текст текст");
    post.setTitle("проба один");
    post.setUser(user);
    post.setViewCount(1);
    post.setModerationStatus(ModerationStatusType.NEW);

    session.save(post);


    }
}
