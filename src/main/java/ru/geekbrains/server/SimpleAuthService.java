package ru.geekbrains.server;


public class SimpleAuthService implements AuthService {

    private final DBHelper dbHelper = DBHelper.getInstance();

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return dbHelper.getNicknameByLoginAndPassword(login, password);
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        return dbHelper.registration(login, password, nickname);
    }

    @Override
    public boolean changeNickName(String login, String newNickName) {
        return dbHelper.changeNickName(login, newNickName);
    }
}
