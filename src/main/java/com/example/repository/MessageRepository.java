package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Message SET messageText=:msgText WHERE messageId=:msgId")
    int updateMessage(@Param("msgText") String msgText, @Param("msgId") int msgId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Message WHERE messageId=:msgId")
    int deleteMessage(@Param("msgId") int msgId);

    @Query("FROM Message WHERE messageId=:msgId")
    Message getMessageFromID(@Param("msgId") int msgId);

    @Query("FROM Message")
    List<Message> getAllMessages();

    @Query("FROM Message WHERE postedBy=:accountId")
    List<Message> getAccountMessages(@Param("accountId") int accountId);
}
