package com.personal.diplom.repository;

import com.personal.diplom.model.PostComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<PostComment,Integer> {

}
