package com.DT.journal.controller;

import com.DT.journal.dto.UserDTO;
import com.DT.journal.entity.User;
import com.DT.journal.service.UserDetailServiceImpl;
import com.DT.journal.service.UserEntryService;
import com.DT.journal.utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/public")
@Tag(name = "Public APIs",  description = "Health check, Sign-Up for a new user and login")
public class PublicController {
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "System is working fine";
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> createUser (@RequestBody UserDTO user ){
        try {
            User newUser = new User();
            newUser.setUserName(user.getUserName());
            newUser.setPassWord(user.getPassword());
            newUser.setEmail(user.getEmail());
            newUser.setCity(user.getCity());
            newUser.setSentimentAnalysis(user.isSentimentAnalysis());
            newUser.setRoles(List.of("USER"));
            userEntryService.saveNewUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User newUser){
        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(newUser.getUserName(), newUser.getPassWord()));
//            UserDetails userDetails = userDetailService.loadUserByUsername(newUser.getUserName());
//            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            String jwt = jwtUtil.generateToken(newUser.getUserName());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while create jwtAuthenticationToken", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.NOT_FOUND);
        }
    }
}
