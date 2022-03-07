package com.personal.diplom.Servise;

import com.personal.diplom.api.response.SettingsResponse;
import com.personal.diplom.model.GlobalSettings;
import com.personal.diplom.repository.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SettingsServise {

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;


    public SettingsResponse getGlobalService() {

    try {
        SettingsResponse settingsResponse = new SettingsResponse();
        Iterable<GlobalSettings> globalSettingsIterable = globalSettingsRepository.findAll();
        for(GlobalSettings globalSettings : globalSettingsIterable) {
            switch (globalSettings.getCode()) {
                case ("MULTIUSER_MODE"):
                    settingsResponse.setMultiuserMode(globalSettings.getValue().equals("YES") ? true : false);
                    break;
                case ("POST_PREMODERATION"):
                    settingsResponse.setPostPremoderation(globalSettings.getValue().equals("YES") ? true : false);
                    break;
                case ("STATISTICS_IS_PUBLIC"):
                    settingsResponse.setStatisticsIsPublic(globalSettings.getValue().equals("YES") ? true : false);
                    break;
            }
        }
         return settingsResponse;
    }catch (Exception e){
        return null;
    }
    }
}
