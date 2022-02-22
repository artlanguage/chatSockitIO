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
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("DATA SENT SUCCESSFULLY");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");

            namespace.getBroadcastOperations().sendEvent("chat", data);

            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("DATA="+data.toString());
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");

            repo.save(new Messages(data));

            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("DATA STORED IN DB SUCCESSFULLY");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            log.debug("Client[{}] - Connected to chat module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("CONNECTED SUCCESSFULLY");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");


        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.debug("Client[{}] - Disconnected from chat module.", client.getSessionId().toString());
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("DISCONNECTED SUCCESSFULLY");
            System.err.println("===========================");
            System.err.println("===========================");
            System.err.println("===========================");
        };
    }

}
