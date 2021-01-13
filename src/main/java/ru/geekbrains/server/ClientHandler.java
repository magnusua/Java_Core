package ru.geekbrains.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private String nickname;
    private String login;
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;


    public String getLogin() {
        return login;
    }

    public String getNickname() {
        return nickname;
    }


    public ClientHandler(Server server, Socket socket, AuthService authService) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    waitAuthorization(server);
                    waitMessageOrCommand(server, authService);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    ClientHandler.this.disconnect();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitAuthorization(Server server) throws IOException { //методе авторизации
        while (true) {
            String msg = in.readUTF();
            if (msg.startsWith("/auth ")) {
                String[] tokens = msg.split("\\s", 3);
                String nick = server.getAuthService().getNicknameByLoginAndPassword(tokens[1], tokens[2]);
                if (nick != null && !server.isNickBusy(nick)) {
                    sendMsg("/authok " + nick);
                    nickname = nick;
                    login = tokens[1];
                    server.subscribe(this);
                    break;
                } // Добавить
            }
        }
    }

    public void waitMessageOrCommand(Server server, AuthService authService) throws IOException {
        while (true) {
            String msg = in.readUTF();
            if (msg.startsWith("/")) {
                if (msg.equals("/end")) {
                    sendMsg("/end");
                    break;
                }
                if (msg.startsWith("/w ")) {
                    String[] tokens = msg.split("\\s", 3);
                    server.privateMsg(this, tokens[1], tokens[2]);
                }
                checkUpdateNickNameCommand(server, authService, msg);
            } else {
                server.broadcastMsg(nickname + ": " + msg);
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkUpdateNickNameCommand(Server server, AuthService authService, String msg){
        if (msg.startsWith("/upnick ")) {
            String[] tokens = msg.split("\\s", 2);
            if (tokens.length == 2 && !tokens[1].trim().equals("")) {
                String newNickName = tokens[1].trim();
                if (!server.isNickBusy(newNickName) && authService.changeNickName(getLogin(), newNickName)) {
                    server.broadcastMsg(nickname + " был изменен на " + newNickName);
                    nickname = newNickName;
                    server.broadcastClientsList();
                } //Добавить исключения
            }
            server.privateMsg(this, tokens[1], tokens[2]);
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
