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
                client.setOnline(true);
                return client;
            }
        }
        return null;
    }
//Проект переноса данных пользователей в базу данных
/*
    // Соединение с базой данных
    private static Connection connection;
    // Запрос в базу данных
    private static Statement stmt;

    public static void connect() {
        //регистрация драйвера
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //хеширование пароля
    public static void addUser(String login, String pass, String nick) {
        try {
            String query = "INSERT INTO main (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs =  stmt.executeQuery("SELECT nickname, password FROM main WHERE login = '" + login + "'");
            int myHash = pass.hashCode();
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash==dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */

}