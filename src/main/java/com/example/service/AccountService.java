package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.custom.Pair;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountFromUsername(String username) {
        return accountRepository.getAccountFromUsername(username);
    }

    /*
     * username length not blank &&
     * password length >= 4 &&
     * username does not exist
     * new account object, flase
     * 
     * else
     * if username exists
     * null, true
     * else
     * null, false
     */
    public Pair<Account, Boolean> registerAccount(Account acc) {
        if (acc != null && !acc.getUsername().isBlank() && acc.getPassword().length() >= 4) {
            // Check if the account already exists
            if (getAccountFromUsername(acc.getUsername()) == null) {
                // Register the account and return a Pair
                Account registeredAccount = accountRepository.save(acc);
                return new Pair<Account, Boolean>(registeredAccount, false);
            } else {
                // Account with the same username already exists
                return new Pair<Account, Boolean>(null, true); // true indicates account exists
            }
        }

        // Invalid account details
        return new Pair<Account, Boolean>(null, false); // false indicates registration failed
    }

    public Account getAccountFromID(int account_id) {
        return accountRepository.getAccountFromID(account_id);
    }
}
