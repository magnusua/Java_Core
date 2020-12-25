package ru.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ChatApiHandler {

    interface Callback {
        void onAuth(boolean isSuccess, String serverError);

        void onNewMessage(String message);
    }

    Callback callback;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    ChatApiHandler(Callback callback) {
        this.callback = callback;
        try {
            Socket socket = new Socket("localhost", Server.PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                while (true) {
                    try {
                        String newMessage = dataInputStream.readUTF();
                        if (newMessage.startsWith("/auth")) {
                            System.out.println(newMessage);
                            if (newMessage.equals("/auth ok")) {
                                System.out.println("response success");
                                callback.onAuth(true, null);
                            } else {
                                callback.onAuth(false, newMessage);
                            }
                        } else if (newMessage.startsWith("/nm ") && newMessage.length() > 5) {
                            callback.onAuth(false, newMessage.substring(4));
                            callback.onNewMessage(newMessage);
                        }
                    } catch (IOException ignored) {
                        System.out.println("Выход 1");
                    }
                }
            }).start();
        } catch (IOException e) {
            System.out.println("Выход 2");

        }
    }

    synchronized void auth(String login, String password) {

        new Thread(() -> {
            try {
                String command = "/auth " + login + " " + password;
                System.out.println(command);
                dataOutputStream.writeUTF(command);
            } catch (IOException e) {
                System.out.println("response exception");
                callback.onAuth(false, null);
            }

        }).start();
        Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(11), 120, TimeUnit.SECONDS);
    }

    synchronized void sendMessage(String text) {
        new Thread(() -> {
            try {
                dataOutputStream.writeUTF(text);
            } catch (IOException e) {
                System.out.println("Ops, something go wrong");
            }
        }).start();
    }
}


