package com.poit.archiveclient;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String username;
    private String password;

    public Client(Socket socket, String username, String password) {
        try {
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.username = username;
            this.password = password;
        } catch (IOException e) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }

    public static void main(String[] args) throws IOException {
        var scanner = new Scanner(System.in);

        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        System.out.println("Enter port");
        int port = scanner.nextInt();

        try {
            var socket = new Socket(InetAddress.getLocalHost(), port);
            var client = new Client(socket, username, password);
            client.listenForMessage();
            client.sendMessage();
        } catch (ConnectException e) {
            System.out.println("There is no server on such port");
        } catch (UnknownHostException e) {
            System.out.println("There is no such host");
        } catch (SocketException e) {
            System.out.println("There is a problem with creating user socket");
        }

    }

    private void sendMessage() {
        try {
            sendCredits();
            var scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String clientInput = scanner.nextLine();
                dataOutputStream.writeInt(clientInput.getBytes(StandardCharsets.UTF_8).length);
                dataOutputStream.write(clientInput.getBytes(StandardCharsets.UTF_8));
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }

    private void sendCredits() throws IOException {
        dataOutputStream.writeInt(username.getBytes(StandardCharsets.UTF_8).length);
        dataOutputStream.write(username.getBytes(StandardCharsets.UTF_8));
        dataOutputStream.flush();

        dataOutputStream.writeInt(password.getBytes(StandardCharsets.UTF_8).length);
        dataOutputStream.write(password.getBytes(StandardCharsets.UTF_8));
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

    private void listenForMessage() {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    int messageLength = dataInputStream.readInt();
                    String msg = new String(dataInputStream.readNBytes(messageLength),
                        StandardCharsets.UTF_8);
                    System.out.println(msg);
                } catch (IOException e) {
                    closeEverything(socket, dataInputStream, dataOutputStream);
                }
            }
        }).start();
    }
}
