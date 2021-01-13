package ru.geekbrains.server;

public interface AuthService {
    String getNicknameByLoginAndPassword(String login, String password);

    boolean registration(String login, String password, String nickname);

    boolean changeNickName(String login, String newNickName);

}