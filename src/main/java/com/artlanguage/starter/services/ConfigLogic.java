package com.artlanguage.starter.services;

import com.artlanguage.starter.repository.configurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigLogic {

    public static String USER_API;

    @Autowired
    configurationRepository repository;

    public void setConstants(){
        try {
            USER_API=repository.findByName("USER_API").getContent();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
