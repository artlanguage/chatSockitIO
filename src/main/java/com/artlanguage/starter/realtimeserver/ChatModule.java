package com.artlanguage.starter.realtimeserver;

import com.artlanguage.starter.models.ChatMessage;
import com.artlanguage.starter.models.Messages;
import com.artlanguage.starter.repository.MessagesRepo;
import com.artlanguage.starter.services.MessagesLogic;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatModule {

    private static final Logger log = LoggerFactory.getLogger(ChatModule.class);

    private final SocketIONamespace namespace;
    private MessagesLogic logic = new MessagesLogic();

    @Autowired
    MessagesRepo repo;

    @Autowired
    public ChatModule(SocketIOServer server) {
        this.namespace = server.addNamespace("/chat");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("chat", ChatMessage.class, onChatReceived());
    }

    private DataListener<ChatMessage> onChatReceived() {
        return (client, data, ackSender) -> {
            namespace.getBroadcastOperations().sendEvent("chat", data);
//            logic.saveMessage(new Messages(data.getUserName(),data.getMessage()));
            System.err.println("repo="+repo);
            repo.save(new Messages(data.getUserName(), data.getMessage()));
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            log.debug("Client[{}] - Connected to chat module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.debug("Client[{}] - Disconnected from chat module.", client.getSessionId().toString());
        };
    }

}
