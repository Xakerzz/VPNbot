package com.xakerz.VPNbot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.xakerz.VPNbot.KeysVPN.setHashMapKeyId;
import static com.xakerz.VPNbot.StatusPayment.setHashMapStatusPayment;


public class Main {
    public static void main(String[] args) {
        setHashMapKeyId();
        setHashMapStatusPayment();
        DataStorage.setHashMapChatId();
        KeysVPN.readDataFromFile();
        Path currentPath = Paths.get("").toAbsolutePath();
        System.out.println("Текущая рабочая директория: " + currentPath);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new VpNbotApplication());
        } catch (TelegramApiException e) {

            throw new RuntimeException(e);
        }
    }
}
