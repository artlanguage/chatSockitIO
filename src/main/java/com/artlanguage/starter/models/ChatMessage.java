package com.artlanguage.starter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatMessage {

    private int senderId;
    private int receiverId;
    private String message;
    private String isSeen;
    private String isRead;


}
