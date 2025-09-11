package com.dunglv.chat.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.dunglv.chat.dto.request.IntrospectRequest;
import com.dunglv.chat.service.IdentityService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SocketHandler {
    SocketIOServer server;
    IdentityService identityService;
    @OnConnect
    public void onConnect(SocketIOClient client) {
        // Get token from request parameters
        String token = client.getHandshakeData().getSingleUrlParam("token");

        // verify token (this is a placeholder, implement your own logic)
        var introspectResponse = identityService.introspect(
               IntrospectRequest.builder()
                        .token(token)
                        .build()
        );

        // If token is invalid, disconnect the client
        if (introspectResponse.isValid()) {
            log.info("Client connected: {}", client.getSessionId());
        } else {
            log.error("Authentication fail: {}", client.getSessionId());
            client.disconnect();
        }
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        log.info("Client disconnected: {}", client.getSessionId());
    }
    @PostConstruct // This method will be called after the bean is constructed
    public void startServer() {
        server.start();
        server.addListeners(this);
        log.info("Socket.IO server started on port {}", server.getConfiguration().getPort());
    }

    @PreDestroy
    public void stopServer() {
        server.stop();
        log.info("Socket.IO server stopped.");
    }
}
