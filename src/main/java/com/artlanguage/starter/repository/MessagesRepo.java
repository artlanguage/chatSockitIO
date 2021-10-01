package com.artlanguage.starter.repository;

import com.artlanguage.starter.models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessagesRepo extends JpaRepository<Messages, Integer> {


    @Transactional
    @Override
    <S extends Messages> S save(S s);

    @Query(value = "select * from messages where sender_id in (?1,?2) and receiver_id in (?3,?4) " +
            "ORDER BY date ASC ;", nativeQuery = true)
    List<Messages> getAllChatMessagesBetweenTwoUsers(int user1, int user2, int user11, int user22);


    @Transactional
    @Query(value = "SELECT * from messages where sender_id=?1 group by receiver_id;", nativeQuery = true)
    List<Messages> getAllUserChats(int userid);


    @Query(value = "select * from messages where sender_id =?1 and receiver_id=?2" +
            " ORDER BY date DESC;", nativeQuery = true)
    List<Messages> getLastMessageBetweenTwoUsers(int userid1, int userid2);


}
