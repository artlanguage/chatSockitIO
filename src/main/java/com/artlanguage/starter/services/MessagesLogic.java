package com.artlanguage.starter.services;


import com.artlanguage.starter.models.ChatsList;
import com.artlanguage.starter.models.Messages;
import com.artlanguage.starter.repository.MessagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessagesLogic {

    @Autowired
    MessagesRepo messagesRepo;



    public Map<Object, Object> saveMessage(Messages m) {

        Map<Object, Object> errMsg = new HashMap<>();
        m = messagesRepo.save(m);
        if (m == null) {
            errMsg.put("error", "NOT ADDED CODE");
        } else
            errMsg.put("success", "success code , message saved successfully");

        return errMsg;

    }//end of logicSendMessage

    public List<Map<Object, Object>> logicGetChatMessages(int user1, int user2) {

        Map<Object, Object> errMsg = new HashMap<>();
        List<Map<Object, Object>> res = new ArrayList<>();

                List<Messages> messagesList = messagesRepo.
                        getAllChatMessagesBetweenTwoUsers(user1, user2, user1, user2);
                for (int i = 0; i < messagesList.size(); i++) {
                    Map<Object, Object> obj = new HashMap<>();
                    obj.put("sender_id", messagesList.get(i).getSenderId());
                    obj.put("receiver_id", messagesList.get(i).getReceiverId());
                    obj.put("message", messagesList.get(i).getMessage());
                    obj.put("date", messagesList.get(i).getDate());
                    res.add(obj);
                }
                return res;
    }

    public List<Map<Object, Object>> logicGetAllUserChatsList(int userid) {
        List<ChatsList> chatsLists = new ArrayList<>();
        List<Map<Object, Object>> res = new ArrayList<>();
        try {
                List<Messages> usersIds = messagesRepo.getAllUserChats(userid);
            System.err.println(usersIds);
                chatsLists.clear();
                for (int i = 0; i < usersIds.size(); i++) {
                    ChatsList cl = new ChatsList();
                    cl.setUserId(usersIds.get(i).getReceiverId());
                    // the returned message raw from db
                    Messages mStatus = messagesRepo.getLastMessageBetweenTwoUsers
                            (userid, usersIds.get(i).getReceiverId()).get(0);
                    cl.setLastMsgSenderId(mStatus.getSenderId());
                    cl.setLastMsg(mStatus.getMessage());
                    cl.setLastMsgDate(mStatus.getDate());

                    chatsLists.add(cl);
                }

        } catch (Exception e) {
            addNullUserToList(chatsLists, e.getMessage());
        } finally {
            if (chatsLists.get(0).getUserId() != 0) {
                for (int i = 0; i < chatsLists.size(); i++) {
                    Map<Object, Object> obj = new HashMap<>();
                    obj.put("user_id", chatsLists.get(i).getUserId());
                    obj.put("lastMgs", chatsLists.get(i).getLastMsg());
                    obj.put("lastMgsSenderId", chatsLists.get(i).getLastMsgSenderId());
                    obj.put("lastMgsDate", chatsLists.get(i).getLastMsgDate());
                    res.add(obj);
                }
            } else {
                Map<Object, Object> obj = new HashMap<>();
                obj.put("error", "THIS USER DOES NOT TALK TO ANY ONE, THE RETURNED LIST IS EMPTY");
                res.add(obj);
            }
            return res;
        }
    }//end of logicGetAllUserChatsList

    private void addNullUserToList(List<ChatsList> chatsLists, String msg) {
        ChatsList nullUser = new ChatsList();
        nullUser.setUserId(0);
        nullUser.setLastMsg(msg);
        chatsLists.clear();
        chatsLists.add(nullUser);
    }

}
