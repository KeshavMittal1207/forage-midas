package com.jpmc.midascore.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;

@Component
public class KafkaListenerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRecordRepository transactionRecordRepository;
    @Autowired
    private RestTemplate restTemplate;      


    @KafkaListener(topics = "${general.kafka-topic}" , groupId = "midas-group")
    void listen(Transaction transaction){
        if(userRepository.findById(transaction.getRecipientId()) == null || userRepository.findById(transaction.getSenderId()) == null || userRepository.findById(transaction.getSenderId()).get().getBalance() > transaction.getAmount()){
            return;
        }
        else{
            Incentive i = restTemplate.postForObject("http://localhost:8080/incentive", transaction, Incentive.class);
            UserRecord sender = userRepository.findById(transaction.getSenderId()).get();
            UserRecord recipient = userRepository.findById(transaction.getRecipientId()).get();

            TransactionRecord transactionRecord = TransactionRecord.builder()
            .amount(transaction.getAmount())
            .recipient(recipient)
            .sender(sender)
            .incentive(i.getAmount())
            .build();

            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + i.getAmount());

            transactionRecordRepository.save(transactionRecord);

            userRepository.save(sender);
            userRepository.save(recipient);
            System.out.println("wilbur " + userRepository.findByName("wilbur").get().getBalance());
        }
    }

}
