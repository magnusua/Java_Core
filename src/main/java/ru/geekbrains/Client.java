package ru.geekbrains;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static int port = 18002;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", port);
            // входящий поток
            Scanner in = new Scanner(socket.getInputStream());
            // исходящий поток
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // с консоли
            Scanner sc = new Scanner(System.in);

            // получаем в этом потоке информацию от сервера
            new Thread(() -> {
                while (true) {
                    String msg = in.nextLine();
                    System.out.println("Server: " + msg);
                }
            }).start();

            // в главном потоке отправляем сообщение серверу
            while (true) {
                System.out.println("Write you message...");
                String msg = sc.nextLine();
                // сообщение улетело серверу
                out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

