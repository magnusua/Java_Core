package ru.geekbrains;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientSignInWindow extends JFrame {

    private final JLabel error;

    interface Callback {
        void onLoginClick(String login, String password);
    }

    ClientSignInWindow(Callback callback) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setBounds(500, 500, 300, 120);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Пожалуйста авторизуйтесь"));
        JTextField loginField = new JTextField();
        loginField.setName("Логин");
        JTextField passwordField = new JTextField();
        passwordField.setName("Пароль");
        JButton signInButton = new JButton("Войти");

        signInButton.addActionListener((e) -> {
            callback.onLoginClick(loginField.getText(), passwordField.getText());
        });
        signInButton.addKeyListener(new KeyAdapter() {
                                        @Override
                                        public void keyPressed(KeyEvent e) {
                                            if (!loginField.getText().equals("") && !passwordField.getText().equals("") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                                callback.onLoginClick(loginField.getText(), passwordField.getText());
                                            }
                                        }
                                    }
        );
        error = new JLabel();
        add(loginField);
        add(passwordField);
        add(signInButton);
        add(error);
    }

    void showError(String errorText) {
        error.setText(errorText);
    }
}
