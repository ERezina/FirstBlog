package com.personal.diplom.Servise;

import com.personal.diplom.api.request.SettingsRequest;
import com.personal.diplom.api.response.ResponseResult;
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
    public void setGlobalSettings(String code, boolean value){
        GlobalSettings globalSettings = globalSettingsRepository.findByCode(code);
        globalSettings.setValue(value?"YES":"NO");
        globalSettingsRepository.save(globalSettings);
    }
    public ResponseResult setGlobalSettingsAll(SettingsResponse settingsResponse){
        ResponseResult responseResult = new ResponseResult();
        setGlobalSettings("MULTIUSER_MODE",settingsResponse.isMultiuserMode());
        setGlobalSettings("POST_PREMODERATION",settingsResponse.isPostPremoderation());
        setGlobalSettings("STATISTICS_IS_PUBLIC",settingsResponse.isStatisticsIsPublic());
        responseResult.setResult(true);
        return responseResult;
    }
}
