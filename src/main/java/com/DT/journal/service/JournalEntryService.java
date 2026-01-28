package com.DT.journal.service;

import com.DT.journal.entity.JournalEntry;
import com.DT.journal.entity.User;
import com.DT.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryrepository;

    @Autowired
    private UserEntryService userentryservice;


    //Controller ---> service ---> Repository

    @Transactional
    public void saveEntry(JournalEntry journalentries, String userName){
        try {
            User user = userentryservice.findbyUserName(userName);
            journalentries.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryrepository.save(journalentries);
            user.getJournalEntries().add(savedEntry);
            userentryservice.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry",e);
        }

    }

    public void saveEntry(JournalEntry journalentries){
        journalEntryrepository.save(journalentries);
    }

    public List<JournalEntry> getAll(){
        return journalEntryrepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryrepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean isFound = false;
        try {
            User user = userentryservice.findbyUserName(userName);
            isFound = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (isFound){
                userentryservice.saveEntry(user);
                journalEntryrepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting entries ", e);
        }
        return isFound;
    }

}
