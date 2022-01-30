package com.personal.diplom.repository;

import com.personal.diplom.model.GlobalSettings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings,Integer> {

    GlobalSettings findByCode(@Param("code") String code);

}
