package com.personal.diplom.repository;

import com.personal.diplom.api.response.TagsWeightResponse;
import com.personal.diplom.model.CountTags;
import com.personal.diplom.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Collection;

@Repository
public interface TagRepository extends CrudRepository<Tag,Integer> {

    @Query(value = "SELECT t.name as name, count(p.post_id) as weight FROM tags t " +
            "LEFT OUTER JOIN tag2post p ON  t.id = p.tag_id " +
            "LEFT OUTER JOIN posts po ON p.post_id = po.id "+
            "where (t.name like concat(:startname,'%') or concat(:startname, '%') is null  ) and ((po.is_active = 1 "+
            "and po.moderation_status = 'ACCEPTED' "+
            "and po.date < sysdate()) or po.id is null) "+
            "group by t.name "+
            "order by weight desc ",
            nativeQuery  = true)
    Collection<CountTags> findTags(@Param("startname") String startname);


}
