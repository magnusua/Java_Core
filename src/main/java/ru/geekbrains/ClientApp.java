package ru.geekbrains;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ClientApp implements
        ClientSignInWindow.Callback,
        ClientChatWindow.Callback,
        ChatApiHandler.Callback {

    final ChatApiHandler api;
    final ClientSignInWindow clientSignInWindow;
    final ClientChatWindow clientChatWindow;

    ClientApp() {
        api = new ChatApiHandler(this);
        clientSignInWindow = new ClientSignInWindow(this);
        clientChatWindow = new ClientChatWindow(this);
        showSignInWindow();
        new Timer().schedule(new TimerTask() {
            public void run() {
                try {
                    hideSignInWindow();
                    api.dataInputStream.close();
                    api.dataOutputStream.close();
                    api.callback.onAuth(false, null);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Timer exception");
                }
            }
        }, 120000);

    }

    @Override
    public void onLoginClick(String login, String password) {
        api.auth(login, password);
    }

    @Override
    public synchronized void onAuth(boolean isSuccess, String serverError) {
        //System.out.println("login: " + isSuccess);
        if (isSuccess) {
            showChatWindow(); // Открываем окно чата
            hideSignInWindow();
        } else {
            clientSignInWindow.showError(serverError);
        }
    }

    @Override
    public void sendMessage(String text) {
        api.sendMessage(text);
    }

    @Override
    public void onNewMessage(String message) {
        synchronized (clientChatWindow) {
            clientChatWindow.onNewMessage(message);
        }
    }

    private void showSignInWindow() {
        clientSignInWindow.setVisible(true);
    }

    private void hideSignInWindow() {
        clientSignInWindow.setVisible(false);
    }

    private void showChatWindow() {
        clientChatWindow.setVisible(true);
    }

    private void hideChatWindow() {
        clientChatWindow.setVisible(false);
    }

    public static void main(String[] args) {
        new ClientApp();
    }
}

