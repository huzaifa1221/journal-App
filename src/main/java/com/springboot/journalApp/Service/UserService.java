package com.springboot.journalApp.Service;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepo UserRepo ;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getAll()
    {
        return UserRepo.findAll();
    }

    public User findUserbyusername(String username)
    {
        return UserRepo.findByuserName(username);
    }

    public void postEntry(User entry)
    {
        UserRepo.save(entry);
    }

    public void deleteUser(String username)
    {
        User user = findUserbyusername(username);
        if(user != null) {
            UserRepo.delete(user);
        }
    }

    public List<User> getadmins (String role)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("roles").is(role));
        List<User> found = mongoTemplate.find(query, User.class);
        return found;
    }
}
