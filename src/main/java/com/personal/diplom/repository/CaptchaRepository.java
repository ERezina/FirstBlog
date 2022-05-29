package com.personal.diplom.repository;

import com.personal.diplom.model.CaptchaCode;
import com.personal.diplom.model.CountTags;
import com.personal.diplom.model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface CaptchaRepository extends CrudRepository<CaptchaCode,Integer>{
    @Modifying
    @Query(value = "delete from CaptchaCode cc where cc.time < :date"           )
    void deleteCaptchaCode(@Param("date") Date date);

    List<CaptchaCode> findBySecretCode(String secret_code);
}
