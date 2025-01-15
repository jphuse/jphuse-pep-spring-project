package com.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message msg) {
        if (!msg.getMessageText().isBlank() && 
            msg.getMessageText().length() < 255 &&
            accountRepository.getAccountFromID(msg.getPostedBy()) != null) {

                return messageRepository.save(msg);
        }

        return null;
    }

    public List<Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }

    public Message getMessageFromID(int messageId) {
        return messageRepository.getMessageFromID(messageId);
    }

    public int deleteMessage(Integer messageId) {
        return messageRepository.deleteMessage(messageId);
    }

        /*
         *  messageId exists &&
         *  messageText is not blank
         *  messageText.length() <= 255
         *      updated msg object
         * 
         *  else
         *      null
         */
    public int updateMessage(Integer messageId, Message msg) {
        Message match = getMessageFromID(messageId);
        if (match != null) {
            if (!msg.getMessageText().isBlank() && msg.getMessageText().length() <= 255) {
                match.setMessageText(msg.getMessageText());
                return messageRepository.updateMessage(match.getMessageText(), messageId);
            }
        }

        return -1;
    }

    public List<Message> getAccountMessages(int account_id) {
        return messageRepository.getAccountMessages(account_id);
    }
}
