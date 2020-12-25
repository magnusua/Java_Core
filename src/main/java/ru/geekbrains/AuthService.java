package ru.geekbrains;

import java.util.ArrayList;
import java.util.List;

class AuthService {
    List<Client> clients = new ArrayList();

    AuthService() {
        clients.add(new Client("Pavel", "pavel", "1234"));
        clients.add(new Client("Oleg", "oleg", "1234"));
        clients.add(new Client("Nick", "nick", "1234"));
    }

    synchronized Client auth(String login, String password) {
        for (Client client : clients) {
            if (client.login.equals(login) && client.password.equals(password)) {
                return client;
            }
        }
        return null;
    }
}