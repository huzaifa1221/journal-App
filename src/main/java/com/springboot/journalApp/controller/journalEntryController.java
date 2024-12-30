package com.springboot.journalApp.controller;

import com.springboot.journalApp.Service.UserService;
import com.springboot.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.Service.journalService;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class journalEntryController
{
    @Autowired
    private journalService journalservice;
    @Autowired
    private UserService userservice;


    @GetMapping("/get-all")
    public ResponseEntity<?> getjournalBYusername()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<JournalEntry> journalEntries =  journalservice.getEntryBYusername(authentication.getName());
        if (!journalEntries.isEmpty()){
            return new ResponseEntity<>(journalEntries,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(journalEntries,HttpStatus.NO_CONTENT);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            journalservice.postEntry(myEntry,authentication.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id,
                                         @RequestBody JournalEntry myEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userservice.findUserbyusername(authentication.getName());
        boolean contains = user.getJournalEntries().stream().anyMatch(x -> x.getId().equals(id));
        if (contains){
          JournalEntry old = journalservice.getEntryBYid(id).orElse(null);
          old.setTitle(!myEntry.getTitle().isEmpty() ? myEntry.getTitle() : old.getTitle());
          old.setContent(!myEntry.getContent().isEmpty() ? myEntry.getContent() : old.getContent());
          journalservice.saveEntry(old,user);
          return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId id) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userservice.findUserbyusername(authentication.getName());
            boolean contains = user.getJournalEntries().stream().anyMatch(x -> x.getId().equals(id));
            if (contains) {
                journalservice.deleteEntry(id,user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

//    @DeleteMapping("delete-all")
//    public ResponseEntity<?> deleteAllJournal() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userservice.findUserbyusername(authentication.getName());
//        journalservice.deleteAllEntries();
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

//    @GetMapping
//    public ResponseEntity<?> getAll()
//    {
//        List<JournalEntry> allEntries = journalservice.getAll();
//        if ( allEntries != null )
//        {
//            return new ResponseEntity<>(allEntries, HttpStatus.OK);
//        }
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
