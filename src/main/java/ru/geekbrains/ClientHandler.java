package ru.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler {
    AuthService authService;
    Server server;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Client client;

    ClientHandler(AuthService authService, Server server, Socket socket) {
        this.authService = authService;
        this.server = server;
        this.socket = socket;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            if (!auth(dataInputStream, dataOutputStream)) {
                // Удаляемся из сервера
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
                server.onClientDisconnected(this);
                return;
            }

            server.onNewClient(this);
            messageListener(dataInputStream);
        } catch (IOException e) {
            // Удаляемся из сервера
            try {
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                System.out.println("Ошибка отключения клиента");
            }
            server.onClientDisconnected(this);
            System.out.println("Клиент отключился");
        }
    }

    //отправка сообщения определенному клиенту
    void sendMessage(Client client, String text) {
        try {
            dataOutputStream.writeUTF(client.name + ": " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //авторизация
    private boolean auth(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        //можно вынести в отдельный метод ServerInfo
        dataOutputStream.writeUTF("""
                Для авторизации начните сообщение с /auth.\s
                Для завершения чата введите /exit или /stop \s
                Вы не авторизированы - введите логин и пароль через пробел!
                 Например: /auth Pavel pavel1""");
        // Цикл ожидания авторизации клиентов
        int tryCount = 0;
        int maxTryCount = 5;
        while (true) {
            // Читаем комманду от клиента
            String newMessage = dataInputStream.readUTF();
            // Разбиваем сообщение на состовляющие комманды
            String[] messageData = newMessage.split("\\s");
            // Проверяем соответсует ли комманда комманде авторизации
            if (messageData.length == 3 && messageData[0].equalsIgnoreCase("/auth")) {
                tryCount++;
                String login = messageData[1];
                String password = messageData[2];
                // Зарегистрирован ли данных пользователь
                client = authService.auth(login, password);
                if (client != null) {
                    // Если получилось авторизоваться то выходим из цикла
                    dataOutputStream.writeUTF("Поздравляю, " + client.name + "Вы успешно подключились. Попыток авторизации: " + tryCount);
                    break;
                } else {
                    dataOutputStream.writeUTF("Неправильные логин или пароль! У вас осталось попыток авторизации: " + tryCount);
                }
            } else {
                dataOutputStream.writeUTF("Ошибка авторизации!");  //можно добавить попытку создания нового клиента
            }
            if (tryCount == maxTryCount) {
                dataOutputStream.writeUTF("Первышен лимит попыток!");
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
                return false;
            }
        }
        return true;
    }

    private void messageListener(DataInputStream dataInputStream) throws IOException {
        while (true) {
            String newMessage = dataInputStream.readUTF();
            if (newMessage.startsWith("/w")) {
                String[] messageData = newMessage.split(" ", 3); //разбиваем строку по пробелам на 3 части
                String nickname = messageData[1];
                String message = messageData[2]; // IntStream.range(2, messageData.length).mapToObj(i -> " " + messageData[i]).collect(Collectors.joining());
                server.sendPrivateMessage(client, nickname, message);
            }
            else if (newMessage.startsWith("/exit") || newMessage.equalsIgnoreCase("/stop")) {
                client.setOnline(false);
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
                server.onClientDisconnected(this);
            } else {
                server.onNewMessage(client, newMessage);
            }
        }
    }
}