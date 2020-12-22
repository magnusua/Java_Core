package ru.geekbrains;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Server {

    protected final int Port = 23154;
    List<ClientHandler> clients = new ArrayList<>();
    Map<String, List<Message>> chats = new HashMap<>();

    Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(Port);
            AuthService authService = new AuthService();
            System.out.println("Server is working...");
            System.out.println("Wait for messages...");
            // Обработчик клиентов
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client");
                new Thread(() -> {
                    new ClientHandler(authService, this, socket);
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Can't open port " + Port);
            System.exit(1);
        }
    }

    synchronized void onNewMessage(Client sender, String text) {
        //вносим сообщения в базу
        String key = "All";
        if (!chats.containsKey(key)) {
            chats.put(key, new ArrayList<>());
        }
        chats.get(key).add(new Message(sender, text));

        // Рассылаем сообщения всем
        for (ClientHandler recipient : clients) {
            //исключаем отправителя
            if (!recipient.client.login.equals(sender.login)) {
                recipient.sendMessage(sender, text);
                System.out.println("Отправлено сообщение от " + sender);
            }
        }
    }

    synchronized void sendPrivateMessage(Client sender, String recipientNickname, String text) {
        String key;
        if (sender.name.compareTo(recipientNickname) > 0) {
            key = sender.name + " " + recipientNickname;
        } else {
            key = recipientNickname + " " + sender.name;
        }
        if (!chats.containsKey(key)) {
            chats.put(key, new ArrayList<>());
        } else {
            chats.get(key).add(new Message(sender, text));
        }
        ClientHandler recipient = null;
        for (ClientHandler client : clients) {
            if (client.client.name.equalsIgnoreCase(recipientNickname)) {
                recipient = client;
            }
        }
        if (recipient != null) {
            recipient.sendMessage(sender, text);
            System.out.println("Отправлено сообщение для " + recipientNickname);
        } else {
            System.out.println("Получатель не найден " + recipientNickname);
            //добавить проверку что никнейм получателя существует через get из списка клиентов
        }

    }


    synchronized void onNewClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        for (int i = 0; i < chats.size(); i++) {
            // Message message = chats.get(i);
            // clientHandler.sendMessage(message.client, message.text);
        }
        onNewMessage(clientHandler.client, "Вошел в чат");
    }

    synchronized void onClientDisconnected(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        onNewMessage(clientHandler.client, "Покинул чат");
    }

    public static void main(String[] args) {
        new Server();
    }
}