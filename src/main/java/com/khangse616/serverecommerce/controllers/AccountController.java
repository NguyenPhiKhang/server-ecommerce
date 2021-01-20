package com.khangse616.serverecommerce.controllers;


import com.khangse616.serverecommerce.models.Account;
import com.khangse616.serverecommerce.repositories.AccountRepository;
import com.khangse616.serverecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/account/add-auto")
    public String addAutoAccount() {
        List<Account> list = userRepository.findAll().stream().map(value -> new Account(String.valueOf(value.getId()), "123456", value)).collect(Collectors.toList());
        accountRepository.saveAll(list);
        return "done";
    }

    @GetMapping("/account/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account account1 = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        return ResponseEntity.ok().body(account1);
    }
}
