package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.custom.Pair;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account acc) {
        // registerAccounht() returns a potential account and boolean pair
        // boolean indicates if the attempt was unsuccessful due to a duplicate error
        Pair<Account, Boolean> result = accountService.registerAccount(acc);

        if (result.getFirst() != null) {
            return ResponseEntity.status(200).body(result.getFirst());
        } else {
            if (result.getSecond()) { //account is null because there was a duplicate
                return ResponseEntity.status(409).body("Username already taken.");
            }

            return ResponseEntity.status(400).body("Client Error.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account acc) {
        Account result = accountService.getAccountFromUsername(acc.getUsername());

        if (result != null) {
            if (result.getPassword().equals(acc.getPassword())) {
                return ResponseEntity.status(200).body(result);
            }
        }

        return ResponseEntity.status(401).body("Unauthorized.");
    }

    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message msg) {
        Message result = messageService.createMessage(msg);
        if (result != null) {
            return ResponseEntity.status(200).body(result);
        }

        return ResponseEntity.status(400).body("Client Error.");
    }

    @GetMapping("/messages")
    public ResponseEntity getAllMessages() {
        List<Message> msgs = messageService.getAllMessages();

        return ResponseEntity.status(200).body(msgs);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity getMessage(@PathVariable("message_id") int message_id) {
        Message result = messageService.getMessageFromID(message_id);

        if (result != null) {
            return ResponseEntity.status(200).body(result);
        }

        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity deleteMessage(@PathVariable("message_id") Integer message_id) {
        int result = messageService.deleteMessage(message_id);

        if (result == 1) {
            return ResponseEntity.status(200).body(result);
        }

        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity updateMessage(@PathVariable("message_id") Integer message_id, @RequestBody Message msg) {
        int result = messageService.updateMessage(message_id, msg);

        if (result == 1) {
            return ResponseEntity.status(200).body(result);
        }

        return ResponseEntity.status(400).body("Client Error.");
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity getAccountMessages(@PathVariable("account_id") int account_id) {
        List<Message> msgs = messageService.getAccountMessages(account_id);

        return ResponseEntity.status(200).body(msgs);
    }
}
