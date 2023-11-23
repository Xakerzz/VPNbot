package com.xakerz.VPNbot;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeysVPN {
    private static final KeysVPN instance = new KeysVPN();
    private static final Map<Long, String> keysMap = new HashMap<>();

    private static final List<String> dataKeys = new ArrayList<>();
    private static final String KEYS_BASE = Paths.get("").toAbsolutePath() + "out/artifacts/VPNbot_jar/Keys.txt";
    private static final String KEYS_ID = Paths.get("").toAbsolutePath() + "out/artifacts/VPNbot_jar/KeysId.txt";

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
        StringBuilder message = null;
        try (BufferedReader br = new BufferedReader(new FileReader(KEYS_ID))) {
            message = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split("   ");

                String[] info = DataStorage.getInstance().getInfoAboutUser(Long.parseLong(temp[0])).split("   ");

                message.append(info[0]).append("   ").append(info[1]).append("   ").append(temp[0]).append("\n").append(temp[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String outMessage = message.toString();
        return outMessage;

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
