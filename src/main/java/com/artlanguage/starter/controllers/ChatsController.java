package com.artlanguage.starter.controllers;

import com.artlanguage.starter.services.MessagesLogic;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatsController {

    @Autowired
    MessagesLogic logic;


    @PostMapping(value = "/getChatMessages", produces = {"application/json"})
    @ResponseBody
    public List<Map<Object, Object>> chatMessages(@RequestBody String requestBody)  {
        JSONObject body = new JSONObject(requestBody);
        Map<Object, Object> errorMsg = new HashMap<>();
        List<Map<Object, Object>> res = new ArrayList<>();
        if (body.isNull("user1")
                || body.isNull("user2")) {
            errorMsg.put("error", "ERROR CODE DOES NOT SEND USERS INFO");
            res.add(errorMsg);
            return res;
        }// end if

        return logic.logicGetChatMessages(body.getInt("user1"), body.getInt("user2"));
    } // end chatMessages


    @PostMapping(value = "/chatsList", produces = {"application/json"})
    @ResponseBody
    public Object chatsList(@RequestBody String requestBody) {
        JSONObject body = new JSONObject(requestBody);
        List<Map<Object, Object>> res = new ArrayList<>();
        Map<Object, Object> msg = new HashMap<>();
        if (body.isNull("userId")) {
            msg.put("error", "ERROR CODE , NOT SEND USER ID");
            res.add(msg);
            return res;
        }
        return logic.logicGetAllUserChatsList(body.optInt("userId") , body.optInt("limit"), body.optInt("offset") );
    } // end chatsList


//    @GetMapping("/messages/{senderId}/{recipientId}/count")
//    public ResponseEntity<Long> countNewMessages(
//            @PathVariable String senderId,
//            @PathVariable String recipientId) {
//
//        return ResponseEntity.ok(
//                chatMessageService.countNewMessages(
//                        senderId, recipientId
//                ));
//    }

//    @GetMapping("/messages/{senderId}/{recipientId}")
//    public ResponseEntity<?> findChatMessages ( @PathVariable String senderId,
//                                                @PathVariable String recipientId) {
//        return ResponseEntity.ok(
//                chatMessageService.findChatMessages(
//                        senderId, recipientId
//                ));
//    }
//
//    @GetMapping("/messages/{id}")
//    public ResponseEntity<?> findMessage ( @PathVariable int id) throws Exception {
//        return ResponseEntity.ok(
//                chatMessageService.findById(id)
//        );
//    }


}
