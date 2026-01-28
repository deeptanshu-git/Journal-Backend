package com.DT.journal.service;

import com.DT.journal.entity.User;
import com.DT.journal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserEntryService {

    @Autowired
    private UserRepository userentryrepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User user){
        try {
            user.setPassWord(passwordEncoder.encode(user.getPassWord()));
            user.setRoles(Arrays.asList("USER"));
            userentryrepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error occured for {} :",user.getUserName(), e);
            throw new RuntimeException(e);
        }

    }

    public void createNewAdmin(User user){
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userentryrepository.save(user);
    }

    public void saveEntry(User user){
        userentryrepository.save(user);
    }

    public List<User> getAll(){
        return userentryrepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userentryrepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userentryrepository.deleteById(id);
    }

    public User findbyUserName(String userName){
        return userentryrepository.findByUserName(userName);
    }

    public void deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userentryrepository.deleteByUserName(authentication.getName());
    }

}
