package com.poit.archive.server;

import com.poit.archive.entity.Role;
import com.poit.archive.entity.User;
import com.poit.archive.server.handlers.AdminHandler;
import com.poit.archive.server.handlers.ClientHandler;
import java.net.Socket;

public class HandlerFactory {

    public Runnable getHandler(User user, Socket socket) {
        return switch (user.getRole()) {
            case USER -> new ClientHandler(socket, user);
            case ADMIN -> new AdminHandler(socket, user);
        };
    }
}
