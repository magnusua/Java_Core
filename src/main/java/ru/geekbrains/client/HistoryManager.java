package ru.geekbrains.client;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HistoryManager {

    private static final int COUNT_LAST_LINES = 100;

    public static void saveHistory(String login, String chatLog) {
        try {
            File history = new File("history_" + login + ".txt");
            if (!history.exists()) {
                history.createNewFile();
            }
            PrintWriter fileWriter = new PrintWriter(new FileWriter(history, true));
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(String.valueOf(chatLog));
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getHistoryFilePath(String login) {
        Path historyPath = Paths.get("History", "history_" + login + ".txt");
        if (Files.notExists(historyPath.getParent())) {
            createHistoryDirectory(historyPath);
        }
        return historyPath;
    }

    private static void createHistoryDirectory(Path path) {
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLinesOfHistory(String login) {
        File history = new File(String.valueOf(getHistoryFilePath(login)));
        try (Stream<String> stream = Files.lines(history.toPath())) {
            return String.valueOf(stream.limit(COUNT_LAST_LINES).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
            return "История пуста";
        }
    }
}