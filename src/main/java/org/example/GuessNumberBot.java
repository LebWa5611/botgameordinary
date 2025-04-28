package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.Random;


public class GuessNumberBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "JavaFirstBot";
    }

    @Override
    public String getBotToken() {
        return "7870658752:AAE8M6nAIRxxQmWHNtOPvXS8YkHzMq_Afp0";
    }
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message == null || message.getText() == null) {
            return;
        }

        String text = message.getText();
        long chatId = message.getChatId();

        if (text.equals("/start")) {
            startGame(chatId);
        } else {
            Integer secretNumber = DataStorage.getSecretNumber(chatId);
            if (secretNumber == -1) {
                sendMessage(chatId, "Для початку гри введіть команду /start");
            } else {
                checkGuess(text, chatId, secretNumber);
            }
        }
    }

    private void startGame(long chatId) {
        int secretNumber = new Random().nextInt(100) + 1; // Генерація випадкового числа від 1 до 100
        DataStorage.updateGameData(chatId, secretNumber); // Записуємо дані
        sendMessage(chatId, "Гра почалась! Вгадай число від 1 до 100.");
    }

    private void checkGuess(String guess, long chatId, int secretNumber) {
        try {
            int userGuess = Integer.parseInt(guess);
            if (userGuess < secretNumber) {
                sendMessage(chatId, "Секретне число більше!");
            } else if (userGuess > secretNumber) {
                sendMessage(chatId, "Секретне число менше!");
            } else {
                sendMessage(chatId, "Вітаємо! Ви вгадали число!");
                DataStorage.updateGameData(chatId, -1); // Скидаємо гру
            }
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Будь ласка, введіть число.");
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}



