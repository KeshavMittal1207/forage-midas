package com.jpmc.midascore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;

@RestController
public class BalanceController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/balance")
    public Balance balance(@RequestParam(value = "userId") Long userId) {
        if(!userRepository.findById(userId).isPresent()){
            return new Balance(0L);
        }else{
            return new Balance(userRepository.findById(userId).get().getBalance());
        }
    }

}
