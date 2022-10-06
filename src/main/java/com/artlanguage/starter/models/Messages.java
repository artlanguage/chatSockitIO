package com.artlanguage.starter.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "sender_id can't be null")
    @Column(name = "sender_id")
    private int senderId;

    @NotNull(message = "receiver_id can't be null")
    @Column(name = "receiver_id")
    private int receiverId;

    @NotNull(message = "message can't be null")
    @Size(min = 1, message = "message can't be less than 1 char")
    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private String date;


    @Column(name = "is_seen")
    private String isSeen;


    @Column(name = "is_read")
    private String isRead;


    public Messages(ChatMessage chatMessage){
        this.message = chatMessage.getMessage();
        this.isSeen = chatMessage.getIsSeen();
        this.isRead= chatMessage.getIsRead();
        this.receiverId=chatMessage.getReceiverId();
        this.senderId=chatMessage.getSenderId();
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}
