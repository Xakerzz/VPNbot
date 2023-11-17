package com.xakerz.VPNbot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static com.xakerz.VPNbot.KeysVPN.setHashMapKeyId;
import static com.xakerz.VPNbot.StatusPayment.setHashMapStatusPayment;


public class Main {
    public static void main(String[] args) {
        setHashMapKeyId();
        setHashMapStatusPayment();
        DataStorage.setHashMapChatId();
        KeysVPN.readDataFromFile();

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new VpNbotApplication());
        } catch (TelegramApiException e) {

            throw new RuntimeException(e);
        }
    }
}
