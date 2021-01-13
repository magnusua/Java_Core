package ru.geekbrains.sever;

public interface AuthService {
    String getNicknameByLoginAndPassword(String login, String password);
}
