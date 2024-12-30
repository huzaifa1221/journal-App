package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.springboot.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class admin {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAlluser()
    {
        List<User> users = userService.getAll();
        if (users != null && !users.isEmpty())
        {
        return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
            user.setRoles(Arrays.asList("admin"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.postEntry(user);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get-all-admins")
    public ResponseEntity<?> getAlladmins()
    {
        List<User> users = userService.getadmins("admin");
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
