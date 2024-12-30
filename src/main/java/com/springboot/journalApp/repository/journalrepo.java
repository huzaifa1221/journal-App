package com.springboot.journalApp.repository;

import com.springboot.journalApp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalrepo extends MongoRepository<JournalEntry,String>
{

}
