package com.xakerz.VPNbot;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static final DataStorage instance = new DataStorage();
    private static final Map<Long, String> infoAboutUser = new HashMap<>();
    private static final String CHAT_ID_BASE = Paths.get("").toAbsolutePath() + "/out/artifacts/VPNbot_jar/DataBase.txt";

    public static void putChatIdToFile(Long chatId, String userNameFirstName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CHAT_ID_BASE, true))) {

            writer.println(chatId + "   " + userNameFirstName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setHashMapChatId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CHAT_ID_BASE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("   ", 3);

                if (parts.length == 3) {
                    try {
                        long chatId = Long.parseLong(parts[0]);
                        String userName = parts[1];
                        String firstName = parts[2];
                        DataStorage.infoAboutUser.put(chatId, userName + "   " + firstName);
                        DataStorageFoChatId.getInstance().setChatId(userName + "   " + firstName, chatId);


                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка преобразования числа: " + e.getMessage());
                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBaseClientId() {
        StringBuilder message = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(CHAT_ID_BASE))) {
            String line;

            while ((line = reader.readLine()) != null) {


                assert message != null;
                message.append(line + "\n");

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return message.toString();
    }


    private DataStorage() {
    }

    public static DataStorage getInstance() {
        return instance;
    }

    public void setInfoAboutUser(long chatId, String userNameFirstName) {
        infoAboutUser.put(chatId, userNameFirstName);
        putChatIdToFile(chatId, userNameFirstName);
    }

    public String getInfoAboutUser(long chatId) {
        return infoAboutUser.get(chatId);
    }

    public void removeInfoAboutUser(long chatId) {
        infoAboutUser.remove(chatId);
    }

    public int getCountUsers() {
        return infoAboutUser.size();
    }
}
