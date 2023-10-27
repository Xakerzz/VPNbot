package com.xakerz.VPNbot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class VpNbotApplication extends TelegramLongPollingBot {

    final private String BOT_NAME = "TrrustvpnBot";
    final private String BOT_TOKEN = "6543447074:AAERjrCy-6UKP1Ja75_jpT2fTYqnVsdtsJc";


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String userMessage = message.getText();
            long chatId = message.getChatId();

            if (userMessage.equals("/start")) {
                sendPhotoMessage(chatId, "https://disk.yandex.ru/i/oHjfUDxCk0XghA");
                messageText(chatId,"Добро пожаловать в наш сервис по продаже\n" +
                        "подписки на VPN на частном сервере.\n" +
                        "При помощи нашего VPN вы получите постоянный\n" +
                        "доступ к любимым сервисам, сайтам, приложениям\n","Подробнее о OpenVPN", "Получить подписку на Vpn", "aboutVPN", "subForVPN");

            }

        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callBackData = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            if (callBackData.equals("aboutVPN")) {
editMessageText(chatId, messageId, "OpenVPN - это популярное и открытое программное обеспечение для создания виртуальных частных сетей (VPN). Он предоставляет безопасное и шифрованное соединение между компьютерами через несекурные сети, такие как интернет. Вот некоторые ключевые характеристики OpenVPN:\n" +
        "\n\n" +
        "1. Открытое ПО: OpenVPN является свободным и открытым программным обеспечением, что означает, что его исходный код доступен для общественности. Это способствует проверке безопасности и доверию.\n" +
        "\n" +
        "2. Кроссплатформенность: OpenVPN поддерживает различные операционные системы, включая Windows, macOS, Linux, iOS и Android, что делает его универсальным для пользователей на разных устройствах.\n" +
        "\n" +
        "3. Шифрование: OpenVPN использует мощное шифрование для обеспечения конфиденциальности данных во время передачи. Это делает его надежным для защиты данных в открытых сетях.",
        "Назад", "Получить подписочку на VPN", "backToMainMenu", "subForVPN");
            } else if (callBackData.equals("backToMainMenu")) {
                editMessageText(chatId,messageId,"Добро пожаловать в наш сервис по продаже\n" +
                        "подписки на VPN на частном сервере.\n" +
                        "При помощи нашего VPN вы получите постоянный\n" +
                        "доступ к любимым сервисам, сайтам, приложениям\n","Подробнее о OpenVPN", "Получить подписку на Vpn", "aboutVPN", "subForVPN");

            } else if (callBackData.equals("Button_pressed")) {

            }

        }
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    private void sendTextMessage(long chatId, String textMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendPhotoMessage(long chatId, String pathToPhotoFile) {
        SendPhoto sendPhoto = new SendPhoto();
        InputFile inputFile = new InputFile(pathToPhotoFile);
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(inputFile);

        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void editMessageText(Long chatId, Integer messageId, String newTextForMessage, String newTextForButtonOne, String newTextForButtonTwo, String newTextForCallbackOne, String newTextForCallbackTwo) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.setText(newTextForMessage);
        editMessage.setReplyMarkup(getInlineKeyboard(newTextForButtonOne, newTextForButtonTwo, newTextForCallbackOne, newTextForCallbackTwo));
        try {
            execute(editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void messageText(Long chatId, String newTextForMessage, String newTextForButtonOne, String newTextForButtonTwo, String newTextForCallbackOne, String newTextForCallbackTwo) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(newTextForMessage);
        sendMessage.setReplyMarkup(getInlineKeyboard(newTextForButtonOne, newTextForButtonTwo, newTextForCallbackOne, newTextForCallbackTwo));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup getInlineKeyboard(String newTextForButtonOne, String newTextForButtonTwo, String newTextForCallbackOne, String newTextForCallbackTwo) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(newTextForButtonOne);
        inlineKeyboardButton.setCallbackData(String.valueOf(newTextForCallbackOne));
        row.add(inlineKeyboardButton);

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(newTextForButtonTwo);
        inlineKeyboardButton1.setCallbackData(String.valueOf(newTextForCallbackTwo));
        row.add(inlineKeyboardButton1);


        keyboard.add(row);


        markup.setKeyboard(keyboard);
        return markup;
    }

}




