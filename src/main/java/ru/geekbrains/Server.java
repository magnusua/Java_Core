package ru.geekbrains;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    static int port = 18002;
    static AtomicInteger clientCount = new AtomicInteger(0);

    public static void main(String[] args) {

        try {
            // Создаём сервер
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is working...");
            System.out.println("Wait for messages...");
            // Ждём клиентов
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client");
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't open port " + port);
            System.exit(1);
        }
    }
}

class ClientHandler extends Thread {
    Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // входящий поток
            Scanner in = new Scanner(socket.getInputStream());
            // исходящий поток
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //или пишем внизу метод
            // с консоли
            Scanner sc = new Scanner(System.in);

// отправляем в этом потоке сообщение от сервера
            new Thread(() -> {
                while (true) {
                    System.out.println("Server, write you message");
                    String msg = sc.nextLine();
                    System.out.println("The message was send");
                    out.println(msg);
                }
            }).start();
            // в главном потоке получаем сообщение и шлем эхо
            while (true) {
                String msg = in.nextLine();
                if (msg.equals("/end")) break;
                System.out.println("Client: " + msg);
                //               out.flush(); // это автоматический/принудительный сброс буфера вывода
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // закрываем розетку клиента
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

