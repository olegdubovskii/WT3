package com.poit.archive.server.handlers;

import com.poit.archive.dao.impl.DossierDAOImpl;
import com.poit.archive.entity.User;
import com.poit.archive.repository.impl.DossierRepositoryImpl;
import com.poit.archive.service.DossierService;
import com.poit.archive.service.impl.DossierServiceImpl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {

    private Socket socket;
    private User user;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private DossierService dossierService;

    public ClientHandler(User user) {
        this.user = user;
    }

    public ClientHandler() {
    }

    public ClientHandler(Socket socket, User user) {
        try {
            this.socket = socket;
            this.user = user;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dossierService = new DossierServiceImpl(
                new DossierRepositoryImpl(new DossierDAOImpl()));
        } catch (IOException e) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String initMessage = """
                    1. Show all dossiers
                    2. Create dossier
                    3. Change dossier
                    """;

                sendMessage(initMessage);

                var msg = getMessageFromClient();

                switch (msg) {
                    case "1" -> showAllDossiers();
                }
            } catch (IOException e) {
                closeEverything(socket, dataInputStream, dataOutputStream);
                break;
            }
        }
    }

    private void showAllDossiers() throws IOException {
        sendMessage(dossierService.findAll().toString());
    }

    private String getMessageFromClient() throws IOException {
        int messageLength = dataInputStream.readInt();
        return new String(dataInputStream.readNBytes(messageLength), StandardCharsets.UTF_8);

    }

    private void sendMessage(String msg) throws IOException {
        dataOutputStream.writeInt(msg.getBytes(StandardCharsets.UTF_8).length);
        dataOutputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        dataOutputStream.flush();
    }

    private void closeEverything(Socket socket,
        DataInputStream dataInputStream,
        DataOutputStream dataOutputStream) {
        try {
            if (socket != null) {
                socket.close();
            }
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
