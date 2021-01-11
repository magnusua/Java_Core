package ru.geekbrains;

import java.sql.*;

public class SQLService {
    private static Connection connection;
    private static PreparedStatement psGetNick;
    private static PreparedStatement psReg;
    private static PreparedStatement psChangeNick;

    private static final String urlConnectionDatabase = "jdbc:sqlite:Users.db";

    public static boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(urlConnectionDatabase);
            prepareAllStatements();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void prepareAllStatements() throws SQLException {
        psGetNick = connection.prepareStatement("SELECT name FROM users WHERE login = ? AND password = ?;");
        psReg = connection.prepareStatement("INSERT INTO users (login, password, name) VALUES (?, ?, ?);");
        psChangeNick = connection.prepareStatement("UPDATE users SET name = ? WHERE login = ?;");
    }

    public static String getNicknameByLoginAndPassword(String login, String password) {
        String nickName = null;
        try {
            psGetNick.setString(1, login);
            psGetNick.setString(2, password);
            ResultSet resultSet = psGetNick.executeQuery();
            if (resultSet.next()) {
                nickName = resultSet.getString("name");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nickName;
    }

    public static boolean registration(String login, String password, String nickname) {
        try {
            psReg.setString(1, login);
            psReg.setString(2, password);
            psReg.setString(3, nickname);
            psReg.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeNick(String login, String newNickName) {
        try {
            psChangeNick.setString(1, newNickName);
            psChangeNick.setString(2, login);
            psChangeNick.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disconnect() {
        try {
            psReg.close();
            psGetNick.close();
            psChangeNick.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
