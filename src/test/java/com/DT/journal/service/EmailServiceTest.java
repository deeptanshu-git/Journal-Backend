package com.DT.journal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void sendMailTest(){
        emailService.sendEMail("tiwarishivam58@gmail.com", "Java Test Mail", "Hi this is a test mail");
    }
}
