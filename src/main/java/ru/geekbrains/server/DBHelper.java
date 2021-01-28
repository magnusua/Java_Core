package ru.geekbrains.server;

import java.sql.*;
import java.util.logging.Level;

import static ru.geekbrains.server.MainServer.logger;

public class DBHelper implements AutoCloseable {
    private static DBHelper instance;
    private static Connection connection;

    private static PreparedStatement psGetNick;
    private static PreparedStatement psReg;
    private static PreparedStatement psChangeNick;

    private static final String urlConnectionDatabase = "jdbc:sqlite:Users.db";

    private DBHelper() { //синглетон
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            loadDriverAndOpenConnection();
            instance = new DBHelper();
        }
        return instance;
    }

    public static void loadDriverAndOpenConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(urlConnectionDatabase);
            prepareAllStatements();
            logger.log(Level.INFO,
                    "logging: {0} ", "База данных успешно подключена");
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING,
                    "logging: {0} ", "Ошибка открытия базы данных");
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
            logger.log(Level.WARNING,
                    "logging: {0} ", "Ошибка получения пользователя из базы данных");
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
            logger.log(Level.WARNING,
                    "logging: {0} ", "Ошибка записи нового пользователя в базу данных");
            return false;
        }
    }

    public static boolean changeNickName(String login, String newNickName) {
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

    @Override
    public void close() {
        try {
            psReg.close();
            psGetNick.close();
            psChangeNick.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.WARNING,
                    "logging: {0} ", "Ошибка отключения от базы данных");
        }
    }
}
