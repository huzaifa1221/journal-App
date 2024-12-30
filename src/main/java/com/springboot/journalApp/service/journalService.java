package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.journalrepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class journalService
{
    @Autowired
    private journalrepo journalEntryRepo;
    @Autowired
    private UserService userService;


    public List<JournalEntry> getAll()
    {
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getEntryBYid(ObjectId id)
    {
        return journalEntryRepo.findById(String.valueOf(id));
    }

    public List<JournalEntry> getEntryBYusername(String username)
    {
        User user = userService.findUserbyusername(username);
        return (user.getJournalEntries());
    }

    @Transactional
    public void postEntry(JournalEntry entry, String username)
    {
        User user = userService.findUserbyusername(username);
        JournalEntry saved = journalEntryRepo.save(entry);
        user.getJournalEntries().add(saved);
        userService.postEntry(user);
    }

    public void saveEntry(JournalEntry entry,User user)
    {
        JournalEntry saved = journalEntryRepo.save(entry);
        userService.postEntry(user);
    }

    public void deleteEntry(ObjectId id, User user) {
        JournalEntry entry = getEntryBYid(id).orElse(null);
        journalEntryRepo.deleteById(String.valueOf(id));
        user.getJournalEntries().remove(entry);
        userService.postEntry(user);
    }

//    public void deleteAllEntries() {
//         journalEntryRepo.deleteAll();
//    }

}
