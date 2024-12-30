package com.springboot.journalApp.controller;

import com.springboot.journalApp.Service.UserService;
import com.springboot.journalApp.Service.WeatherService;
import com.springboot.journalApp.api.WeatherResponse;
import com.springboot.journalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController
@Slf4j
@RequestMapping("/public")
public class Public {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/health-check")
    public ResponseEntity<?> healthcheck()
    {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/weather")
    public ResponseEntity<?> weather(@RequestBody String city)
    {
        WeatherResponse response =  weatherService.report(city);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> addUser(@RequestBody User user)
    {
        if (user.getRoles() == null){
            user.setRoles(Arrays.asList("user"));
        }
        if (user.getRoles().contains("admin"))
        {
            log.error("you cannot create a user with role ADMIN");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.postEntry(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
