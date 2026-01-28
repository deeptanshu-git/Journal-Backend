package com.DT.journal.controller;

import com.DT.journal.api.response.WeatherResponse;
import com.DT.journal.entity.User;
import com.DT.journal.repository.UserRepository;
import com.DT.journal.service.UserEntryService;
import com.DT.journal.service.WeatherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "Welcome, delete and update a user")
public class UserEntryController {
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeatherService weatherService;

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userEntryService.findbyUserName(userName);
        userInDb.setUserName(updatedUser.getUserName());
        userInDb.setPassWord(updatedUser.getPassWord());
        userEntryService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete-user")
    public ResponseEntity<?>deleteUser(){
        userEntryService.deleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userRepository.findByUserName(userName);
        WeatherResponse weather = weatherService.getWeather(user.getCity());
        String weatherResponse ="";
        if(weather != null){
            weatherResponse = " the current temperature is "+ weather.getCurrent().getTemperature()
            +" & it feels like "+weather.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+authentication.getName() + weatherResponse, HttpStatus.OK);
    }
}