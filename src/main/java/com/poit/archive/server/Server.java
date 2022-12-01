package com.poit.archive.server;

import com.poit.archive.dao.impl.UserDAOImpl;
import com.poit.archive.repository.impl.UserRepositoryImpl;
import com.poit.archive.service.UserService;
import com.poit.archive.service.impl.UserServiceImpl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server {

    private final ServerSocket serverSocket;
    private final HandlerFactory handlerFactory = new HandlerFactory();
    private final UserService userService;

    public Server(ServerSocket serverSocket, UserService userService) {
        this.serverSocket = serverSocket;
        this.userService = userService;
    }

    public static void main(String[] args) throws IOException {
        try {
            var scanner = new Scanner(System.in);
            System.out.println("Enter port number:");
            var serverSocket = new ServerSocket(scanner.nextInt());
            var server = new Server(serverSocket, new UserServiceImpl(new UserRepositoryImpl(new UserDAOImpl())));
            server.startServer();
        } catch (BindException e) {
            System.out.println("This port is already in use, try another one");
        }
    }

    private void startServer() {
        try {
            System.out.println("Server is running . . .");
            while (!serverSocket.isClosed()) {
                var socket = serverSocket.accept();
                System.out.println("Someone has connected");
                var dataInputStream = new DataInputStream(socket.getInputStream());

                int loginLength = dataInputStream.readInt();
                var login = new String(dataInputStream.readNBytes(loginLength), StandardCharsets.UTF_8);

                int passwordLength = dataInputStream.readInt();
                var password = new String(dataInputStream.readNBytes(passwordLength), StandardCharsets.UTF_8);

                var user = userService.signIn(login, password);
                if (user == null) return;

                var clientHandler = handlerFactory.getHandler(user, socket);

                var thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
