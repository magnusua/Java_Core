package ru.geekbrains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientChatWindow extends JFrame {

    final JTextArea messagesList;
    private static final int Size = 600;

    interface Callback {

        void sendMessage(String text);
    }

    ClientChatWindow(Callback callback) {
        setBounds(Size, Size, Size, Size);
        setLayout(new GridLayout(2, 1));
        // Список сообщений
        messagesList = new JTextArea();
        messagesList.setEditable(false);
        add(messagesList);
        // Панель новго сообщения
        JPanel sendMassagePanel = new JPanel();
        sendMassagePanel.setLayout(new BoxLayout(sendMassagePanel, BoxLayout.X_AXIS));
        sendMassagePanel.setSize(600, 50);
        JTextField messageField = new JTextField();
        JButton sendButton = new JButton("Отправить");
        //настройки окна
        setTitle("Чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //выравнивание по центру
        sendMassagePanel.add(messageField);
        sendMassagePanel.add(sendButton);
        add(sendMassagePanel);
        sendButton.addActionListener((e) -> {
            if (!messageField.getText().equals("")){
            callback.sendMessage(messageField.getText());
                messageField.setText("");
                    }});
        sendButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!messageField.getText().equals("") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    callback.sendMessage(messageField.getText());
                    messageField.setText("");
                }
            }
        });
    }

    public void onNewMessage(String message) {
        messagesList.setText(messagesList.getText() + "\n" + message);
    }
}
