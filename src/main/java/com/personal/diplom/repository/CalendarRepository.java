package com.personal.diplom.repository;

import com.personal.diplom.model.CountPostByDate;
import com.personal.diplom.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CalendarRepository extends CrudRepository<Post,Integer> {

    @Query(value = "SELECT  DATE_FORMAT(p.date, '%d/%m/%Y') as date, count(1) as countPost " +
            "FROM posts p " +
            "WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.date <= sysdate() " +
           "and EXTRACT( YEAR FROM p.date) = :year " +
            "Group by DATE_FORMAT(p.date, '%d/%m/%Y') ",
            nativeQuery = true
    )
    Collection<CountPostByDate> GetCountPost(@Param("year") Integer year);
}
