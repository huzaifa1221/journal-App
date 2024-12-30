package com.springboot.journalApp.repository;

import com.springboot.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User,String>
{
    User findByuserName(String username );
}
