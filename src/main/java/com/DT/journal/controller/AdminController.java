package com.DT.journal.controller;

import com.DT.journal.cache.AppCache;
import com.DT.journal.entity.User;
import com.DT.journal.service.UserEntryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs" , description = "Get to see all users, create a new admin and clear App-cache")
public class AdminController {

    @Autowired
    private AppCache appCache;

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers = userEntryService.getAll();
        if(allUsers != null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-new-admin")
    public void createNewAdmin(@RequestBody User user){
        userEntryService.createNewAdmin(user);
    }
    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.initializeMap();
    }
}
