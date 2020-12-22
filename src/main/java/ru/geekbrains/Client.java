package ru.geekbrains;

class Client {
    String name;
    String login;
    String password;


    Boolean online;

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Client(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}