package com.xakerz.VPNbot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


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

            System.out.println(System.getProperty("user.dir"));
            Statistic.setAmountUsersEntry();


            setUserName("@" + update.getMessage().getFrom().getUserName());
            setFirstName(update.getMessage().getFrom().getFirstName());

            System.out.println(getUserName());
            System.out.println(getFirstName());


            Message message = update.getMessage();
            String userMessage = message.getText();
            setChatId(message.getChatId());
            if (DataStorageFoChatId.getInstance().getChatId(getUserName() + CommandsForBot.MARK_SPACE.getCommand() + getFirstName()) == null ||
                    !DataStorageFoChatId.getInstance().getChatId(getUserName() + CommandsForBot.MARK_SPACE.getCommand() + getFirstName()).equals(getChatId())) {

                DataStorage.getInstance().setInfoAboutUser(getChatId(), getUserName() + CommandsForBot.MARK_SPACE.getCommand() + getFirstName());
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

            } else if (update.hasMessage() && userMessage.equals(CommandsForBot.STATISTIC.getCommand()) && getChatId() == BotConfig.ADMIN_ID()) {

                sendStatisticMessage(BotConfig.getCHAT_ID_CHANEL(), new Statistic().toString());
            } else if (update.hasMessage() && userMessage.equals(CommandsForBot.GET_LIST_CLIENTS.getCommand()) && getChatId() == BotConfig.ADMIN_ID()) {
                sendStatisticMessage(BotConfig.getCHAT_ID_CHANEL(), KeysVPN.readDataFromFileToStatistic());
            } else if (update.hasMessage() && userMessage.equals(CommandsForBot.GET_CLIENT_BASE.getCommand()) && getChatId() == BotConfig.ADMIN_ID()) {
                sendStatisticMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getBaseClientId());
            }

        } else if (update.hasCallbackQuery()) {


            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callBackData = callbackQuery.getData();
            setChatId(callbackQuery.getMessage().getChatId());
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            if (callBackData.contains(CallBackForButtons.SUCCESS.getCallBack()) || callBackData.contains(CallBackForButtons.NOT_SUCCESS.getCallBack())) {
                String[] callBackArray = callBackData.trim().split(CommandsForBot.MARK_SPACE.getCommand());
                callBackData = callBackArray[0];
                setChatId(DataStorageFoChatId.getInstance().getChatId(callBackArray[1] + CommandsForBot.MARK_SPACE.getCommand() + callBackArray[2]));

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
                        BotButtons.BACK.getTextButton(), BotButtons.ORDER_OWN_VPN_SERVER.getTextButton(),
                        CallBackForButtons.BACK_TO_MONTH_MENU.getCallBack(), CallBackForButtons.PAY_FOR_OWN_SERVER.getCallBack(), Links.ORDER_SERVER.getLink());

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
                if (StatusPayment.getStatusPayment(getChatId())) {
                    sendTextMessage(getChatId(),Phrases.SUCCESS_PAYMENT_AGAIN.getPhrase());
                    sendTextMessage(getChatId(), KeysVPN.getHashMapChatId(getChatId()));
                } else {
                    messageText(BotConfig.getCHAT_ID_PAYMENT_CHANEL(),
                            Phrases.MESSAGE_PAYMENT.getPhrase() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                            BotButtons.BUTTON_NOT_PAYMENT.getTextButton(), BotButtons.BUTTON_PAYMENT.getTextButton(),
                            CallBackForButtons.NOT_SUCCESS.getCallBack() + CommandsForBot.MARK_SPACE.getCommand() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                            CallBackForButtons.SUCCESS.getCallBack() + CommandsForBot.MARK_SPACE.getCommand() + DataStorage.getInstance().getInfoAboutUser(getChatId()));
                }

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.PRESS_PAY_BUTTON_ONE_MONTH.getBotLog());

                ToLog.log(BotLogs.PRESS_PAY_BUTTON_ONE_MONTH.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

                System.out.println(getChatId());

            } else if (callBackData.equals(CallBackForButtons.PAID_FOR_THREE_MONTHS.getCallBack())) {

                Statistic.setPressPaidThreeMonthsButton();
                if (StatusPayment.getStatusPayment(getChatId())) {
                    sendTextMessage(getChatId(),Phrases.SUCCESS_PAYMENT_AGAIN.getPhrase());
                    sendTextMessage(getChatId(), KeysVPN.getHashMapChatId(getChatId()));
                } else {
                    messageText(BotConfig.getCHAT_ID_PAYMENT_CHANEL(),
                            Phrases.MESSAGE_PAYMENT.getPhrase() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                            BotButtons.BUTTON_NOT_PAYMENT.getTextButton(), BotButtons.BUTTON_PAYMENT.getTextButton(),
                            CallBackForButtons.NOT_SUCCESS.getCallBack() + CommandsForBot.MARK_SPACE.getCommand() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                            CallBackForButtons.SUCCESS.getCallBack() + CommandsForBot.MARK_SPACE.getCommand() + DataStorage.getInstance().getInfoAboutUser(getChatId()));
                }

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.PRESS_PAY_BUTTON_THREE_MONTHS.getBotLog());

                ToLog.log(BotLogs.PRESS_PAY_BUTTON_THREE_MONTHS.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

                System.out.println(getChatId());

            } else if (callBackData.equals(CallBackForButtons.PAID_FOR_SIX_MONTHS.getCallBack())) {

                Statistic.setPressPaidSixMonthsButton();
                if (StatusPayment.getStatusPayment(getChatId())) {
                    sendTextMessage(getChatId(),Phrases.SUCCESS_PAYMENT_AGAIN.getPhrase());
                    sendTextMessage(getChatId(), KeysVPN.getHashMapChatId(getChatId()));
                } else {
                    messageText(BotConfig.getCHAT_ID_PAYMENT_CHANEL(),
                            Phrases.MESSAGE_PAYMENT.getPhrase() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                            BotButtons.BUTTON_NOT_PAYMENT.getTextButton(), BotButtons.BUTTON_PAYMENT.getTextButton(),
                            CallBackForButtons.NOT_SUCCESS.getCallBack() + CommandsForBot.MARK_SPACE.getCommand() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                            CallBackForButtons.SUCCESS.getCallBack() + CommandsForBot.MARK_SPACE.getCommand() + DataStorage.getInstance().getInfoAboutUser(getChatId()));
                }

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.PRESS_PAY_BUTTON_SIX_MONTHS.getBotLog());

                ToLog.log(BotLogs.PRESS_PAY_BUTTON_SIX_MONTHS.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));


            } else if (callBackData.equals(CallBackForButtons.PAID_FOR_TWELVE_MONTHS.getCallBack())) {

                Statistic.setPressPaidTwelveMonthsButton();
                if (StatusPayment.getStatusPayment(getChatId())) {
                    sendTextMessage(getChatId(),Phrases.SUCCESS_PAYMENT_AGAIN.getPhrase());
                    sendTextMessage(getChatId(), KeysVPN.getHashMapChatId(getChatId()));
                } else {
                    messageText(BotConfig.getCHAT_ID_PAYMENT_CHANEL(),
                            Phrases.MESSAGE_PAYMENT.getPhrase() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                            BotButtons.BUTTON_NOT_PAYMENT.getTextButton(), BotButtons.BUTTON_PAYMENT.getTextButton(),
                            CallBackForButtons.NOT_SUCCESS.getCallBack() + CommandsForBot.MARK_SPACE.getCommand() + DataStorage.getInstance().getInfoAboutUser(getChatId()),
                            CallBackForButtons.SUCCESS.getCallBack() + CommandsForBot.MARK_SPACE.getCommand() + DataStorage.getInstance().getInfoAboutUser(getChatId()));
                }

                sendTextMessage(BotConfig.getCHAT_ID_CHANEL(), DataStorage.getInstance().getInfoAboutUser(getChatId()) +
                        BotLogs.PRESS_PAY_BUTTON_TWELVE_MONTHS.getBotLog());

                ToLog.log(BotLogs.PRESS_PAY_BUTTON_TWELVE_MONTHS.getBotLog(), getChatId(), DataStorage.getInstance().getInfoAboutUser(getChatId()));

                System.out.println(getChatId());

            } else if (callBackData.equals(CallBackForButtons.NOT_SUCCESS.getCallBack())) {

                Statistic.setNotSuccessPaymentCounter();

                sendTextMessage(getChatId(), Phrases.NOT_SUCCESS_PAYMENT.getPhrase());
            } else if (callBackData.equals(CallBackForButtons.SUCCESS.getCallBack())) {

                Statistic.setSuccessPaymentCounter();
                if (!StatusPayment.getStatusPayment(getChatId())) {
                    sendTextMessage(getChatId(), Phrases.SUCCESS_PAYMENT.getPhrase());
                    KeysVPN.setHashMapChatId(getChatId());
                    KeysVPN.putChatIdToFile(getChatId(), KeysVPN.getHashMapChatId(getChatId()));
                    sendTextMessage(getChatId(), KeysVPN.getHashMapChatId(getChatId()));
                    StatusPayment.setStatusPayment(getChatId(), true);
                    StatusPayment.putChatIdToFile(chatId, true);
                    sendTextMessage(getChatId(), Phrases.MESSAGE_AFTER_SUCCESS_PAYMENT.getPhrase());

                }
            }
        }

         if (update.hasMessage() && update.getMessage().hasLocation()) {

            sendLocationToOtherChanel(update);
        } else if (update.hasMessage() && update.getMessage().hasPhoto() && getChatId() != BotConfig.ADMIN_ID()) {
            sendPhotoToOtherChanel(update);

        } else if (update.hasMessage() && update.getMessage().hasText() && getChatId() != BotConfig.ADMIN_ID()) {
            sendTextToOtherChanel(update);

        } else if (update.hasMessage() && update.getMessage().hasVideo()) {
            sendVideoToOtherChanel(update);

        } else if (update.hasMessage() && update.getMessage().hasAudio()) {
            sendAudioToOtherChanel(update);

        } else if (update.hasMessage() && update.getMessage().hasAnimation()) {
            sendAnimationToOtherChanel(update);

        } else if (update.hasMessage() && update.getMessage().hasVoice()) {
            sendVoiceToOtherChanel(update);

        } else if (update.hasMessage() && update.getMessage().hasVideoNote()) {
            sendInVideoNoteToOtherChanel(update);

        }



        if (update.hasMessage() && update.getMessage().hasPhoto() && !StatusPayment.getStatusPayment(update.getMessage().getChatId())) {
            Message message = update.getMessage();
            List<PhotoSize> photos = message.getPhoto();

            getPhoto(photos, update);

        }
        if (update.hasMessage() && getChatId() == BotConfig.ADMIN_ID() && update.getMessage().getText().equals(CommandsForBot.KEYBOARD.getCommand())) {

            getAdminPanel();

        }
        if (update.hasMessage() && getChatId() == BotConfig.ADMIN_ID() && update.getMessage().getText().contains(CommandsForBot.MARK_SS.getCommand())) {
            String[] keysArray = update.getMessage().getText().trim().split("\n");
            List<String> dataKeys = new ArrayList<>(Arrays.asList(keysArray));
            KeysVPN.putKeyToFile(dataKeys);
        }
    }

    private void sendInVideoNoteToOtherChanel(Update update) {
        VideoNote videoNote = update.getMessage().getVideoNote();
        SendVideoNote sendVideoNote = new SendVideoNote();
        sendVideoNote.setChatId(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE());
        InputFile inputFile = new InputFile(videoNote.getFileId());
        sendVideoNote.setVideoNote(inputFile);
        sendTextMessage(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE(), update.getMessage().getChatId() + "   " + DataStorage.getInstance().getInfoAboutUser(update.getMessage().getChatId()));

        try {

            execute(sendVideoNote);


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendVoiceToOtherChanel(Update update) {
        Voice voice = update.getMessage().getVoice();
        SendVoice sendVoice = new SendVoice();
        sendVoice.setCaption(update.getMessage().getChatId() + "   " + DataStorage.getInstance().getInfoAboutUser(update.getMessage().getChatId()));
        sendVoice.setChatId(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE());
        InputFile inputFile = new InputFile(voice.getFileId());
        sendVoice.setVoice(inputFile);

        try {

            execute(sendVoice);


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendAnimationToOtherChanel(Update update) {
        Animation animation = update.getMessage().getAnimation();
        SendAnimation sendAnimation = new SendAnimation();
        sendAnimation.setCaption(update.getMessage().getChatId() + "   " + DataStorage.getInstance().getInfoAboutUser(update.getMessage().getChatId()));
        sendAnimation.setChatId(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE());
        InputFile inputFile = new InputFile(animation.getFileId());
        sendAnimation.setAnimation(inputFile);

        try {

            execute(sendAnimation);


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendAudioToOtherChanel(Update update) {
        Audio audio = update.getMessage().getAudio();
        SendAudio sendAudio = new SendAudio();
        sendAudio.setCaption(update.getMessage().getChatId() + "   " + DataStorage.getInstance().getInfoAboutUser(update.getMessage().getChatId()));
        sendAudio.setChatId(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE());
        InputFile inputFile = new InputFile(audio.getFileId());
        sendAudio.setAudio(inputFile);

        try {

            execute(sendAudio);


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendVideoToOtherChanel(Update update) {
        Video video = update.getMessage().getVideo();
        SendVideo sendVideo = new SendVideo();
        sendVideo.setCaption(update.getMessage().getChatId() + "   " + DataStorage.getInstance().getInfoAboutUser(update.getMessage().getChatId()));
        sendVideo.setChatId(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE());
        InputFile inputFile = new InputFile(video.getFileId());
        sendVideo.setVideo(inputFile);

        try {

            execute(sendVideo);


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTextToOtherChanel(Update update) {
        sendTextMessage(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE(), update.getMessage().getChatId() + "   "
                + DataStorage.getInstance().getInfoAboutUser(update.getMessage().getChatId()));
        sendTextMessage(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE(), update.getMessage().getText());
    }

    private void sendPhotoToOtherChanel(Update update) {
        Message message = update.getMessage();
        List<PhotoSize> photos = message.getPhoto();
        PhotoSize photo = photos.stream()
                .max(Comparator.comparing(PhotoSize::getFileSize))
                .orElse(null);
        if (update.getMessage().hasText()) {
            sendTextMessage(BotConfig.getCHAT_ID_PAYMENT_CHANEL(), update.getMessage().getText());
        }
        if (photo != null) {
            String fileId = photos.get(photos.size() - 1).getFileId();
            sendTextMessage(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE(), DataStorage.getInstance().getInfoAboutUser(update.getMessage().getChatId()));
            sendPhotoMessage(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE(), fileId);

        }
    }

    private void sendLocationToOtherChanel(Update update) {
        Location location = update.getMessage().getLocation();
        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE());
        sendLocation.setLatitude(location.getLatitude());
        sendLocation.setLongitude(location.getLongitude());
        sendTextMessage(BotConfig.getCHAT_ID_OTHER_CONTENT_CHANE(), update.getMessage().getChatId() + "   "
                + DataStorage.getInstance().getInfoAboutUser(update.getMessage().getChatId()));
        try {

            execute(sendLocation);


        } catch (TelegramApiException e) {
            e.printStackTrace();
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

    private void getAdminPanel() {
        SendMessage message = new SendMessage();
        message.setChatId(BotConfig.ADMIN_ID());
        message.setText(Phrases.KEYBOARD_IS_ON.getPhrase());
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(false);
        keyboardMarkup.setResizeKeyboard(true);
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton button = new KeyboardButton(CommandsForBot.GET_STATISTIC.getCommand());
        KeyboardButton button2 = new KeyboardButton(CommandsForBot.GET_LIST_CLIENTS.getCommand());
        KeyboardButton button3 = new KeyboardButton(CommandsForBot.GET_CLIENT_BASE.getCommand());
        row.add(button);
        row.add(button2);
        row2.add(button3);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(row);
        keyboardRowList.add(row2);
        keyboardMarkup.setKeyboard(keyboardRowList);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            sendStatisticMessage(BotConfig.getCHAT_ID_CHANEL(), e.getMessage());
        }
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

    private void getPhoto(List<PhotoSize> photos, Update update) {
        PhotoSize photo = photos.stream()
                .max(Comparator.comparing(PhotoSize::getFileSize))
                .orElse(null);
        if (update.getMessage().hasText()) {
            sendTextMessage(BotConfig.getCHAT_ID_PAYMENT_CHANEL(), update.getMessage().getText());
        }
        if (photo != null) {
            String fileId = photos.get(photos.size() - 1).getFileId();
            sendTextMessage(BotConfig.getCHAT_ID_PAYMENT_CHANEL(), DataStorage.getInstance().getInfoAboutUser(update.getMessage().getChatId()));
            sendPhotoMessage(BotConfig.getCHAT_ID_PAYMENT_CHANEL(), fileId);

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




