package com.jpmc.midascore.services;

import org.springframework.web.bind.annotation.RestController;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ApiController {

    @PostMapping("/incentive")
    public Incentive incentive(@RequestBody Transaction transaction) {
        Incentive incentive = Incentive.builder()
        .amount(transaction.getAmount())
        .build();
        return incentive;
    }
}
