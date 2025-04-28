package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataStorage {

    private static final String FILE_NAME = "game_data.txt";

    // Читання з файлу
    public static Map<Long, Integer> readGameData() {
        Map<Long, Integer> gameData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    gameData.put(Long.parseLong(parts[0]), Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameData;
    }

    // Запис в файл
    public static void writeGameData(Map<Long, Integer> gameData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<Long, Integer> entry : gameData.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Оновлення гри для конкретного користувача
    public static void updateGameData(long userId, int secretNumber) {
        Map<Long, Integer> gameData = readGameData();
        gameData.put(userId, secretNumber);
        writeGameData(gameData);
    }

    // Отримання числа для конкретного користувача
    public static Integer getSecretNumber(long userId) {
        Map<Long, Integer> gameData = readGameData();
        return gameData.getOrDefault(userId, -1);
    }
}

