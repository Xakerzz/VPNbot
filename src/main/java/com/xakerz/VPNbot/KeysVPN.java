package com.xakerz.VPNbot;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class KeysVPN {
    private static final KeysVPN instance = new KeysVPN();
    private static final Map<Long, String> keysMap = new HashMap<>();

    private static final List<String> dataKeys = new ArrayList<>();
    private static final String KEYS_BASE = "/root/VPNbot/VPNbot/out/artifacts/VPNbot_jar/Keys.txt";
    //private static final String KEYS_BASE = "src/main/resources/Files/Keys.txt";
    private static final String KEYS_ID = "/root/VPNbot/VPNbot/out/artifacts/VPNbot_jar/KeysId.txt";
    //private static final String KEYS_ID = "src/main/resources/Files/KeysId.txt";

    public static KeysVPN getInstance() {
        return instance;
    }

    public static Integer getAmountListKeys() {
        return dataKeys.size();
    }

    public static void putChatIdToFile(Long chatId, String key) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(KEYS_ID, true))) {

            writer.println(chatId + "   " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setHashMapKeyId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(KEYS_ID))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("   ", 2);

                if (parts.length == 2) {
                    try {
                        long chatId = Long.parseLong(parts[0]);
                        String key = parts[1];


                        setKeyToMap(chatId, key);


                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка преобразования текста: " + e.getMessage());
                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void setKeyToMap(long chatId, String key) {
        keysMap.put(chatId, key);
    }

    public static void readDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(KEYS_BASE))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataKeys.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String readDataFromFileToStatistic() {
        StringBuilder  message = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(KEYS_ID))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split("   ");

                String[] info = DataStorage.getInstance().getInfoAboutUser(Long.parseLong(temp[0])).split("   ");

                message.append(info[0]).append("   ").append(info[1]).append("   ").append(temp[0]).append("\n").append(temp[1]).append("\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message.toString();

    }

    public static String readKeysFromFileToSend() {
        StringBuilder message = null;
        int counter = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(KEYS_BASE))) {
            message = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {


                message.append(counter + ":" + CommandsForBot.MARK_SPACE.getCommand() + line + "\n\n");
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String outMessage = message.toString();
        return outMessage;

    }

    public static void removeKeysFromFile(String str) {

        String[] keysArray = str.trim().split("\n");
        List<String> keysToRemove = new ArrayList<>(List.of(keysArray));

        Iterator<String> iterator = dataKeys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (keysToRemove.contains(key)) {
                iterator.remove();
            }
        }
        System.out.println("Ключи удалены из базы.");
        reWriteFile();

    }

    public static void setHashMapChatId(long chatId) {
        keysMap.put(chatId, dataKeys.get(dataKeys.size() - 1).trim());
        dataKeys.remove(dataKeys.size() - 1);


        reWriteFile();

    }

    public static String getHashMapChatId(long chatId) {

        return keysMap.get(chatId).toString();

    }

    public static void putKeyToFile(List<String> dataKey) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(KEYS_BASE, true))) {

            for (String ithem : dataKey) {

                writer.write(ithem);
                writer.println();

            }
            dataKeys.addAll(dataKey);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reWriteFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(KEYS_BASE))) {
            for (String ithem : dataKeys) {

                writer.write(ithem + "\n");


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
