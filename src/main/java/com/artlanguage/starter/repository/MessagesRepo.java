package com.artlanguage.starter.repository;

import com.artlanguage.starter.models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface MessagesRepo extends JpaRepository<Messages, Integer> {


    @Transactional
    @Override
    <S extends Messages> S save(S s);

    @Query(value = "select * from messages where sender_id in (?1,?2) and receiver_id in (?3,?4) " +
            "ORDER BY date ASC ;", nativeQuery = true)
    List<Messages> getAllChatMessagesBetweenTwoUsers(int user1, int user2, int user11, int user22);


    @Transactional
    @Query(value = "select mm.sender_id , mm.receiver_id , " +
            "       (select m.message  from messages m " +
            "       where m.sender_id =mm.sender_id and m.receiver_id=mm.receiver_id order by m.date desc limit 1) as 'message' , " +
            "       (select m.date  from messages m " +
            "        where m.sender_id =mm.sender_id and m.receiver_id=mm.receiver_id order by m.date desc limit 1) as 'date' " +
            "from messages mm where mm.sender_id= :sId group by receiver_id order by date desc limit :l offset :o ;", nativeQuery = true)
    List<Map<Object,Object>> getAllUserChats(@Param("sId") int senderId,@Param("l") int limit ,@Param("o") int offset);


    @Transactional
    @Query(value = "select mm.sender_id " +
            "from messages mm where mm.sender_id=:sId group by receiver_id  limit :l offset :o ;", nativeQuery = true)
    List<Map<Object,Object>> countAllUserChats(@Param("sId") int senderId,@Param("l") int limit ,@Param("o") int offset);



}
