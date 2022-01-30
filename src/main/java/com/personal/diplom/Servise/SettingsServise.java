package com.personal.diplom.Servise;

import com.personal.diplom.api.response.SettingsResponse;
import com.personal.diplom.model.GlobalSettings;
import com.personal.diplom.repository.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsServise {

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    public GlobalSettings findByCode(String code){
        GlobalSettings result = globalSettingsRepository.findByCode(code);
        return result;
    }

    public SettingsResponse getGlobalService() {
    try {
        SettingsResponse settingsResponse = new SettingsResponse();
        GlobalSettings globalSettings = new GlobalSettings();
        globalSettings = findByCode("MULTIUSER_MODE");
        if (globalSettings.getValue().equals("YES")) {
            settingsResponse.setMultiuserMode(true);
        } else {
            settingsResponse.setMultiuserMode(false);
        }

        globalSettings = findByCode("POST_PREMODERATION");
        if (globalSettings.getValue().equals("YES")) {
            settingsResponse.setPostPremoderation(true);
        } else {
            settingsResponse.setPostPremoderation(false);
        }

        globalSettings = findByCode("STATISTICS_IS_PUBLIC");
        if (globalSettings.getValue().equals("YES")) {
            settingsResponse.setStatisticsIsPublic(true);
        } else {
            settingsResponse.setStatisticsIsPublic(false);
        }
        return settingsResponse;
    }catch (Exception e){
        return null;
    }
    }
}
