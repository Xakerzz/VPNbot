package com.xakerz.VPNbot;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.telegram.telegrambots.meta.api.objects.File.getFileUrl;


@SpringBootApplication
public class VpNbotApplication extends TelegramLongPollingBot {


    private String userName;
    private String firstName;
    private long chatId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage() && update.getMessage().hasText()) {

            Statistic.setAmountUsersEntry();


            setUserName("@" + update.getMessage().getFrom().getUserName());
            setFirstName(update.getMessage().getFrom().getFirstName());

            System.out.println(getUserName());
            System.out.println(getFirstName());


            Message message = update.getMessage();
            String userMessage = message.getText();
            setChatId(message.getChatId());
            if (!DataStorageFoChatId.getInstance().getChatId(getUserName() + "   " + getFirstName()).equals(message.getChatId())) {
                DataStorage.getInstance().setInfoAboutUser(getChatId(), getUserName() + "   " + getFirstName());
                DataStorageFoChatId.getInstance().setChatId(DataStorage.getInstance().getInfoAboutUser(getChatId()), getChatId());
            }
            if (userMessage.equals(CommandsForBot.START.getCommand())) {
                sendPhotoMessage(getChatId(), Links.WELCOME_PHOTO.getLink());

                messageText(getChatId(), Phrases.WELCOME.getPhrase(),
                        BotButtons.ABOUT_VPN.getTextButton(), BotButtons.GET_SUBSCRIPTION.getTextButton(),
                        CallBackForButtons.ABOUT_VPN.getCallBack(), CallBackForButtons.SUBSCRIPTION_FOR_VPN.getCallBack());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.ENTER_IN_THE_BOT.getBotLog());
                ToLog.log(BotLogs.ENTER_IN_THE_BOT.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (update.hasMessage() && userMessage.equals(CommandsForBot.STATISTIC.getCommand())) {

                sendStatisticMessage(BotConfig.getCHAT_ID_CHANEL(), new Statistic().toString());
            }

        } else if (update.hasCallbackQuery()) {


            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callBackData = callbackQuery.getData();
            setChatId(callbackQuery.getMessage().getChatId());
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            if (callBackData.contains(CallBackForButtons.SUCCESS.getCallBack()) || callBackData.contains(CallBackForButtons.NOT_SUCCESS.getCallBack())) {
                String[] callBackArray = callBackData.trim().split("   ");
                callBackData = callBackArray[0];
                setChatId(DataStorageFoChatId.getInstance().getChatId(callBackArray[1] + "   " + callBackArray[2]));
                System.out.println("into method");
                System.out.println(callBackArray[1] + " " + callBackArray[2]);
                System.out.println(getChatId());
            }

            if (callBackData.equals(CallBackForButtons.ABOUT_VPN.getCallBack())) {

                Statistic.setPressAboutVPNButton();

                editMessageText(getChatId(), messageId, Phrases.ABOUT_VPN.getPhrase(),
                        BotButtons.BACK.getTextButton(), BotButtons.GET_SUBSCRIPTION.getTextButton(),
                        CallBackForButtons.BACK_TO_MAIN_MENU.getCallBack(), CallBackForButtons.SUBSCRIPTION_FOR_VPN_FROM_ABOUT_VPN.getCallBack());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.ENTER_IN_ABOUT_VPN.getBotLog());

                ToLog.log(BotLogs.ENTER_IN_ABOUT_VPN.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.BACK_TO_ABOUT_VPN.getCallBack())) {

                Statistic.setPressBackButton();

                editMessageText(getChatId(), messageId, Phrases.ABOUT_VPN.getPhrase(),
                        BotButtons.BACK.getTextButton(), BotButtons.GET_SUBSCRIPTION.getTextButton(),
                        CallBackForButtons.BACK_TO_MAIN_MENU.getCallBack(), CallBackForButtons.SUBSCRIPTION_FOR_VPN_FROM_ABOUT_VPN.getCallBack());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.BACK_TO_ABOUT_VPN.getBotLog());

                ToLog.log(BotLogs.BACK_TO_ABOUT_VPN.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.BACK_TO_MAIN_MENU.getCallBack())) {

                Statistic.setPressBackButton();

                editMessageText(getChatId(), messageId, Phrases.WELCOME.getPhrase(),
                        BotButtons.ABOUT_VPN.getTextButton(), BotButtons.GET_SUBSCRIPTION.getTextButton(),
                        CallBackForButtons.ABOUT_VPN.getCallBack(), CallBackForButtons.SUBSCRIPTION_FOR_VPN.getCallBack());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.BACK_TO_MAIN_MENU.getBotLog());

                ToLog.log(BotLogs.BACK_TO_MAIN_MENU.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.SUBSCRIPTION_FOR_VPN.getCallBack())) {

                Statistic.setPressSubButton();

                editMessageText(getChatId(), messageId, Phrases.MONTH_MESSAGE.getPhrase(),
                        BotButtons.ONE_MONTH.getTextButton(), BotButtons.THREE_MONTHS.getTextButton(), BotButtons.SIX_MONTHS.getTextButton(),
                        BotButtons.TWELVE_MONTH.getTextButton(), BotButtons.BACK.getTextButton(), BotButtons.GET_OWN_VPN_SERVER.getTextButton(),
                        CallBackForButtons.ONE_MONTH.getCallBack(), CallBackForButtons.THREE_MONTHS.getCallBack(), CallBackForButtons.SIX_MONTHS.getCallBack(),
                        CallBackForButtons.TWELVE_MONTHS.getCallBack(), CallBackForButtons.BACK_TO_MAIN_MENU.getCallBack(), CallBackForButtons.GET_OWN_VPN_SERVER.getCallBack());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.ENTER_SUB_MENU.getBotLog());

                ToLog.log(BotLogs.ENTER_SUB_MENU.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.SUBSCRIPTION_FOR_VPN_FROM_ABOUT_VPN.getCallBack())) {

                Statistic.setPressSubFromAboutVPNButton();

                editMessageText(getChatId(), messageId, Phrases.MONTH_MESSAGE.getPhrase(),
                        BotButtons.ONE_MONTH.getTextButton(), BotButtons.THREE_MONTHS.getTextButton(), BotButtons.SIX_MONTHS.getTextButton(),
                        BotButtons.TWELVE_MONTH.getTextButton(), BotButtons.BACK.getTextButton(), BotButtons.GET_OWN_VPN_SERVER.getTextButton(),
                        CallBackForButtons.ONE_MONTH.getCallBack(), CallBackForButtons.THREE_MONTHS.getCallBack(), CallBackForButtons.SIX_MONTHS.getCallBack(),
                        CallBackForButtons.TWELVE_MONTHS.getCallBack(), CallBackForButtons.BACK_TO_ABOUT_VPN.getCallBack(), CallBackForButtons.GET_OWN_VPN_SERVER.getCallBack());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.TO_SUB_FROM_ABOUT_VPN.getBotLog());

                ToLog.log(BotLogs.TO_SUB_FROM_ABOUT_VPN.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.ONE_MONTH.getCallBack())) {

                Statistic.setPressSubOneMonthButton();

                editMessageText(getChatId(), messageId, Phrases.SUB_FOR_ONE_MONTH.getPhrase(),
                        BotButtons.BACK.getTextButton(), BotButtons.PAY_FOR_ONE_MONTH.getTextButton(), BotButtons.I_PAID.getTextButton(),
                        CallBackForButtons.BACK_TO_MONTH_MENU.getCallBack(), CallBackForButtons.PAY_FOR_ONE_MONTH.getCallBack(), CallBackForButtons.PAID_FOR_ONE_MONTH.getCallBack(),
                        Links.LINK_FOR_PAY.getLink());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.ENTER_IN_THE_ONE_MONTH_SUB.getBotLog());

                ToLog.log(BotLogs.ENTER_IN_THE_ONE_MONTH_SUB.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.THREE_MONTHS.getCallBack())) {

                Statistic.setPressSubThreeMonthsButton();


                editMessageText(getChatId(), messageId, Phrases.SUB_FOR_THREE_MONTHS.getPhrase(),
                        BotButtons.BACK.getTextButton(), BotButtons.PAY_FOR_THREE_MONTH.getTextButton(), BotButtons.I_PAID.getTextButton(),
                        CallBackForButtons.BACK_TO_MONTH_MENU.getCallBack(), CallBackForButtons.PAY_FOR_THREE_MONTH.getCallBack(), CallBackForButtons.PAID_FOR_THREE_MONTHS.getCallBack(),
                        Links.LINK_FOR_PAY.getLink());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.ENTER_IN_THE_THREE_MONTHS_SUB.getBotLog());

                ToLog.log(BotLogs.ENTER_IN_THE_THREE_MONTHS_SUB.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.SIX_MONTHS.getCallBack())) {

                Statistic.setPressSubSixMonthsButton();


                editMessageText(getChatId(), messageId, Phrases.SUB_FOR_SIX_MONTHS.getPhrase(),
                        BotButtons.BACK.getTextButton(), BotButtons.PAY_FOR_SIX_MONTH.getTextButton(), BotButtons.I_PAID.getTextButton(),
                        CallBackForButtons.BACK_TO_MONTH_MENU.getCallBack(), CallBackForButtons.PAY_FOR_SIX_MONTH.getCallBack(), CallBackForButtons.PAID_FOR_SIX_MONTHS.getCallBack(),
                        Links.LINK_FOR_PAY.getLink());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.ENTER_IN_THE_SIX_MONTHS_SUB.getBotLog());

                ToLog.log(BotLogs.ENTER_IN_THE_SIX_MONTHS_SUB.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.TWELVE_MONTHS.getCallBack())) {

                Statistic.setPressSubTwelveMonthsButton();

                editMessageText(getChatId(), messageId, Phrases.SUB_FOR_TWELVE_MONTHS.getPhrase(),
                        BotButtons.BACK.getTextButton(), BotButtons.PAY_FOR_TWELVE_MONTH.getTextButton(), BotButtons.I_PAID.getTextButton(),
                        CallBackForButtons.BACK_TO_MONTH_MENU.getCallBack(), CallBackForButtons.PAY_FOR_TWELVE_MONTH.getCallBack(), CallBackForButtons.PAID_FOR_TWELVE_MONTHS.getCallBack(),
                        Links.LINK_FOR_PAY.getLink());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.ENTER_IN_THE_TWELVE_MONTHS_SUB.getBotLog());

                ToLog.log(BotLogs.ENTER_IN_THE_TWELVE_MONTHS_SUB.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.GET_OWN_VPN_SERVER.getCallBack())) {

                Statistic.setPressGetOwnServerButton();

                editMessageText(getChatId(), messageId, Phrases.GET_OWN_VPN_SERVER.getPhrase(),
                        BotButtons.BACK.getTextButton(), BotButtons.PAY_FOR_ONE_MONTH.getTextButton(),
                        CallBackForButtons.BACK_TO_MONTH_MENU.getCallBack(), CallBackForButtons.PAY_FOR_OWN_SERVER.getCallBack());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.ENTER_IN_GET_OWN_SERVER_MENU.getBotLog());

                ToLog.log(BotLogs.ENTER_IN_GET_OWN_SERVER_MENU.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.BACK_TO_MONTH_MENU.getCallBack())) {

                Statistic.setPressBackButton();

                editMessageText(getChatId(), messageId, Phrases.MONTH_MESSAGE.getPhrase(),
                        BotButtons.ONE_MONTH.getTextButton(), BotButtons.THREE_MONTHS.getTextButton(), BotButtons.SIX_MONTHS.getTextButton(),
                        BotButtons.TWELVE_MONTH.getTextButton(), BotButtons.BACK.getTextButton(), BotButtons.GET_OWN_VPN_SERVER.getTextButton(),
                        CallBackForButtons.ONE_MONTH.getCallBack(), CallBackForButtons.THREE_MONTHS.getCallBack(), CallBackForButtons.SIX_MONTHS.getCallBack(),
                        CallBackForButtons.TWELVE_MONTHS.getCallBack(), CallBackForButtons.BACK_TO_MAIN_MENU.getCallBack(), CallBackForButtons.GET_OWN_VPN_SERVER.getCallBack());

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.BACK_TO_SUB_MENU_FROM_MONTH_MENU.getBotLog());

                ToLog.log(BotLogs.BACK_TO_SUB_MENU_FROM_MONTH_MENU.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

            } else if (callBackData.equals(CallBackForButtons.PAID_FOR_ONE_MONTH.getCallBack())) {

                Statistic.setPressPaidOneMonthButton();

                messageText(BotConfig.getCHAT_ID_PAYMENT_CHANEL(),
                        Phrases.MESSAGE_PAYMENT.getPhrase() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                        BotButtons.BUTTON_NOT_PAYMENT.getTextButton(), BotButtons.BUTTON_PAYMENT.getTextButton(),
                        CallBackForButtons.NOT_SUCCESS.getCallBack() + "   " + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                        CallBackForButtons.SUCCESS.getCallBack() + "   " + DataStorage.getInstance().getInfoAboutUser(getChatId()));

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.PRESS_PAY_BUTTON_ONE_MONTH.getBotLog());

                ToLog.log(BotLogs.PRESS_PAY_BUTTON_ONE_MONTH.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

                System.out.println(getChatId());

            } else if (callBackData.equals(CallBackForButtons.PAID_FOR_THREE_MONTHS.getCallBack())) {

                Statistic.setPressPaidThreeMonthsButton();

                messageText(BotConfig.getCHAT_ID_PAYMENT_CHANEL(),
                        Phrases.MESSAGE_PAYMENT.getPhrase() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                        BotButtons.BUTTON_NOT_PAYMENT.getTextButton(), BotButtons.BUTTON_PAYMENT.getTextButton(),
                        CallBackForButtons.NOT_SUCCESS.getCallBack() + "   " + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                        CallBackForButtons.SUCCESS.getCallBack() + "   " + DataStorage.getInstance().getInfoAboutUser(getChatId()));

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.PRESS_PAY_BUTTON_THREE_MONTHS.getBotLog());

                ToLog.log(BotLogs.PRESS_PAY_BUTTON_THREE_MONTHS.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

                System.out.println(getChatId());

            } else if (callBackData.equals(CallBackForButtons.PAID_FOR_SIX_MONTHS.getCallBack())) {

                Statistic.setPressPaidSixMonthsButton();

                messageText(BotConfig.getCHAT_ID_PAYMENT_CHANEL(),
                        Phrases.MESSAGE_PAYMENT.getPhrase() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                        BotButtons.BUTTON_NOT_PAYMENT.getTextButton(), BotButtons.BUTTON_PAYMENT.getTextButton(),
                        CallBackForButtons.NOT_SUCCESS.getCallBack() + "   " + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                        CallBackForButtons.SUCCESS.getCallBack() + "   " + DataStorage.getInstance().getInfoAboutUser(getChatId()));

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.PRESS_PAY_BUTTON_SIX_MONTHS.getBotLog());

                ToLog.log(BotLogs.PRESS_PAY_BUTTON_SIX_MONTHS.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));


            } else if (callBackData.equals(CallBackForButtons.PAID_FOR_TWELVE_MONTHS.getCallBack())) {

                Statistic.setPressPaidTwelveMonthsButton();

                messageText(BotConfig.getCHAT_ID_PAYMENT_CHANEL(),
                        Phrases.MESSAGE_PAYMENT.getPhrase() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                        BotButtons.BUTTON_NOT_PAYMENT.getTextButton(), BotButtons.BUTTON_PAYMENT.getTextButton(),
                        CallBackForButtons.NOT_SUCCESS.getCallBack() + "   " + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                        CallBackForButtons.SUCCESS.getCallBack() + "   " + DataStorage.getInstance().getInfoAboutUser(getChatId()));

                StatusPayment.getInstance().setStatusPayment(getChatId(), true);

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.PRESS_PAY_BUTTON_TWELVE_MONTHS.getBotLog());

                ToLog.log(BotLogs.PRESS_PAY_BUTTON_TWELVE_MONTHS.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

                System.out.println(getChatId());

            } else if (callBackData.equals(CallBackForButtons.NOT_SUCCESS.getCallBack())) {

                Statistic.setNotSuccessPaymentCounter();

                sendTextMessage(getChatId(), Phrases.NOT_SUCCESS_PAYMENT.getPhrase());
            } else if (callBackData.equals(CallBackForButtons.SUCCESS.getCallBack())) {

                Statistic.setSuccessPaymentCounter();

                sendTextMessage(getChatId(), Phrases.SUCCESS_PAYMENT.getPhrase());
            }

        }

        if (update.hasMessage() && update.getMessage().hasPhoto() && StatusPayment.getInstance().getStatusPayment(update.getMessage().getChatId())) {

            Message message = update.getMessage();
            List<PhotoSize> photos = message.getPhoto();


            PhotoSize photo = photos.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null);

            if (photo != null) {
                String fileId = photos.get(photos.size() - 1).getFileId();

                sendPhotoMessage(BotConfig.getCHAT_ID_PAYMENT_CHANEL(), fileId);
                StatusPayment.getInstance().setStatusPayment(update.getMessage().getChatId(), false);
            }
        }


    }


    @Override
    public String getBotUsername() {
        return BotConfig.getBOT_NAME();
    }

    @Override
    public String getBotToken() {
        return BotConfig.getBOT_TOKEN();
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

    private void sendStatisticMessage(long chatId, String textMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);

        try {
            Message sendStatisticMessage = execute(sendMessage);
            PinChatMessage pinMessage = new PinChatMessage();
            pinMessage.setChatId(chatId);
            pinMessage.setMessageId(sendStatisticMessage.getMessageId());
            execute(pinMessage);
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
            throw new RuntimeException(e.getMessage());
        }
    }

    private void editMessageText(Long chatId, Integer messageId, String newTextForMessage, String newTextForButtonOne, String newTextForButtonTwo, String newTextForCallbackOne, String newTextForCallbackTwo, String url) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.setText(newTextForMessage);
        editMessage.setReplyMarkup(getInlineKeyboard(newTextForButtonOne, newTextForButtonTwo, newTextForCallbackOne, newTextForCallbackTwo, url));
        try {
            execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void editMessageText(Long chatId, Integer messageId, String newTextForMessage, String newTextForButtonOne, String newTextForButtonTwo, String newTextForButtonThree, String newTextForCallbackOne, String newTextForCallbackTwo, String newTextForCallbackThree, String url) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.setText(newTextForMessage);
        editMessage.setReplyMarkup(getInlineKeyboard(newTextForButtonOne, newTextForButtonTwo, newTextForButtonThree, newTextForCallbackOne, newTextForCallbackTwo, newTextForCallbackThree, url));
        try {
            execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void editMessageText(Long chatId, Integer messageId, String newTextForMessage, String newTextForButtonOne, String newTextForButtonTwo, String newTextForButtonThree, String newTextForButtonFour, String newTextForButtonFive, String newTextForButtonSix, String newTextForCallbackOne, String newTextForCallbackTwo, String newTextForCallbackThree, String newTextForCallbackFour, String newTextForCallbackFive, String newTextForCallbackSix) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.setText(newTextForMessage);
        editMessage.setReplyMarkup(getInlineKeyboard(newTextForButtonOne, newTextForButtonTwo, newTextForButtonThree, newTextForButtonFour, newTextForButtonFive, newTextForButtonSix, newTextForCallbackOne, newTextForCallbackTwo, newTextForCallbackThree, newTextForCallbackFour, newTextForCallbackFive, newTextForCallbackSix));
        try {
            execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e.getMessage());
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
            throw new RuntimeException(e.getMessage());
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

    private InlineKeyboardMarkup getInlineKeyboard(String newTextForButtonOne, String newTextForButtonTwo, String newTextForButtonThree, String newTextForCallbackOne, String newTextForCallbackTwo, String newTextForCallbackThree, String url) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(newTextForButtonOne);
        inlineKeyboardButton.setCallbackData(String.valueOf(newTextForCallbackOne));
        row.add(inlineKeyboardButton);

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(newTextForButtonTwo);
        inlineKeyboardButton1.setUrl(url);
        inlineKeyboardButton1.setCallbackData(String.valueOf(newTextForCallbackTwo));
        row.add(inlineKeyboardButton1);

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText(newTextForButtonThree);
        inlineKeyboardButton2.setCallbackData(String.valueOf(newTextForCallbackThree));
        row1.add(inlineKeyboardButton2);


        keyboard.add(row);
        keyboard.add(row1);


        markup.setKeyboard(keyboard);
        return markup;
    }

    private InlineKeyboardMarkup getInlineKeyboard(String newTextForButtonOne, String newTextForButtonTwo, String newTextForCallbackOne, String newTextForCallbackTwo, String url) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(newTextForButtonOne);
        inlineKeyboardButton.setCallbackData(String.valueOf(newTextForCallbackOne));
        row.add(inlineKeyboardButton);

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(newTextForButtonTwo);
        inlineKeyboardButton1.setUrl(url);
        inlineKeyboardButton1.setCallbackData(String.valueOf(newTextForCallbackTwo));
        row.add(inlineKeyboardButton1);


        keyboard.add(row);


        markup.setKeyboard(keyboard);
        return markup;
    }

    private InlineKeyboardMarkup getInlineKeyboard(String newTextForButtonOne, String newTextForButtonTwo, String newTextForButtonThree, String newTextForButtonFour, String newTextForButtonFive, String newTextForButtonSix, String newTextForCallbackOne, String newTextForCallbackTwo, String newTextForCallbackThree, String newTextForCallbackFour, String newTextForCallbackFive, String newTextForCallbackSix) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(newTextForButtonOne);
        inlineKeyboardButton.setCallbackData(String.valueOf(newTextForCallbackOne));
        row.add(inlineKeyboardButton);

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(newTextForButtonTwo);
        inlineKeyboardButton1.setCallbackData(String.valueOf(newTextForCallbackTwo));
        row.add(inlineKeyboardButton1);

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText(newTextForButtonThree);
        inlineKeyboardButton2.setCallbackData(String.valueOf(newTextForCallbackThree));
        row2.add(inlineKeyboardButton2);

        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText(newTextForButtonFour);
        inlineKeyboardButton3.setCallbackData(String.valueOf(newTextForCallbackFour));
        row2.add(inlineKeyboardButton3);

        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText(newTextForButtonSix);
        inlineKeyboardButton4.setCallbackData(String.valueOf(newTextForCallbackSix));
        row3.add(inlineKeyboardButton4);

        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setText(newTextForButtonFive);
        inlineKeyboardButton5.setCallbackData(String.valueOf(newTextForCallbackFive));
        row4.add(inlineKeyboardButton5);


        keyboard.add(row);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);


        markup.setKeyboard(keyboard);
        return markup;
    }

}




