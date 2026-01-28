package com.DT.journal.scheduler;

import com.DT.journal.entity.JournalEntry;
import com.DT.journal.entity.User;
import com.DT.journal.enums.Sentiment;
import com.DT.journal.repository.UserRepositoryImpl;
import com.DT.journal.service.EmailService;
import com.DT.journal.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    
    @Autowired
    private UserRepositoryImpl userRepository;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    
    @Scheduled (cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSaMail(){
        List<User> usersForSA = userRepository.getUsersForSA();
        for (User user : usersForSA){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate()
                    .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment != null){
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0)+1);
                }
                Sentiment mostFrequentSentiment = null;
                int maxCount = 0;
                for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()){
                    if (entry.getValue()>maxCount){
                        maxCount = entry.getValue();
                        mostFrequentSentiment=entry.getKey();
                    }
                }
                if (mostFrequentSentiment !=null){
                    emailService.sendEMail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());
                }
            }
        }
    }
}
