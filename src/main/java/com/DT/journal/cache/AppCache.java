package com.DT.journal.cache;

import com.DT.journal.entity.JournalAppConfig;
import com.DT.journal.repository.JournalAppConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public Map<String, String> App_Cache;

    @Autowired
    private JournalAppConfigRepository journalAppConfigRepository;

    @PostConstruct
    public void initializeMap(){
        App_Cache = new HashMap<>();
        List<JournalAppConfig> all = journalAppConfigRepository.findAll();
        for (JournalAppConfig journalAppConfig: all){
            App_Cache.put(journalAppConfig.getKey(), journalAppConfig.getValue());
        }
    }
}
