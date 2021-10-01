package com.artlanguage.starter.models;

public class ChatsList {
    private int userId;
    private int lastMsgSenderId;
    private String lastMsg;
    private String lastMsgDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLastMsgSenderId() {
        return lastMsgSenderId;
    }

    public void setLastMsgSenderId(int lastMsgSenderId) {
        this.lastMsgSenderId = lastMsgSenderId;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getLastMsgDate() {
        return lastMsgDate;
    }

    public void setLastMsgDate(String lastMsgDate) {
        this.lastMsgDate = lastMsgDate;
    }
}
