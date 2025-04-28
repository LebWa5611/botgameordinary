package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataStorage {

    private static final String FILE_NAME = "game_data.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<Long, Integer> readGameData() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                return objectMapper.readValue(file, HashMap.class);
            } else {
                return new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void writeGameData(Map<Long, Integer> gameData) {
        try {
            objectMapper.writeValue(new File(FILE_NAME), gameData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateGameData(long userId, int secretNumber) {
        Map<Long, Integer> gameData = readGameData();
        gameData.put(userId, secretNumber);
        writeGameData(gameData);
    }

    public static Integer getSecretNumber(long userId) {
        Map<Long, Integer> gameData = readGameData();
        return gameData.getOrDefault(userId, -1);
    }
}
