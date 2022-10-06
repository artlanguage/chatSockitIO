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

    public Object logicGetAllUserChatsList(int sender_id , int limit , int offset ) {

        System.err.println("senderId="+sender_id+" , limit="+limit+" , offset"+offset);
        Map<Object,Object> res = new HashMap<>();
        if (limit==0)
            limit=Integer.MAX_VALUE;
        try {
                List<Map<Object,Object>> messages = messagesRepo.getAllUserChats(sender_id,limit,offset);
                long c = messagesRepo.countAllUserChats(sender_id, Integer.MAX_VALUE, 0).size();

                res.put("messages",messages);
                res.put("total",c);

                return res;

        } catch (Exception e) {
            e.printStackTrace();
            res.put("error",e.getMessage());
            return res;
        }

        }//end of logicGetAllUserChatsList
    }

