package com.xakerz.VPNbot;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class StatusPayment {
    private static final StatusPayment instance = new StatusPayment();
    private static final Map<Long, Boolean> statusPayment = new HashMap<>();

    private static final String STATUS_PAYMENT_BASE = Paths.get("").toAbsolutePath() +"/VPNbot/VPNbot/out/artifacts/VPNbot_jar/StatusPayment.txt";
    //private static final String STATUS_PAYMENT_BASE = "src/main/resources/Files/StatusPayment.txt";

    private StatusPayment() {
    }

    public static void putChatIdToFile(Long chatId, Boolean statusPayment) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STATUS_PAYMENT_BASE, true))) {

            writer.println(chatId + "   " + statusPayment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setHashMapStatusPayment() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STATUS_PAYMENT_BASE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("   ", 2);

                if (parts.length == 2) {
                    try {
                        long chatId = Long.parseLong(parts[0]);
                        boolean statusPayment = Boolean.parseBoolean(parts[1]);


                        setStatusPayment(chatId, statusPayment);



                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка преобразования числа или булиан переменной: " + e.getMessage());
                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StatusPayment getInstance() {
        return instance;
    }

    public static void setStatusPayment(long chatId, Boolean statusPaymentBoolean) {

            statusPayment.put(chatId, statusPaymentBoolean);


    }

    public static Boolean getStatusPayment(long chatId) {
        if (statusPayment.get(chatId) == null) {
            return false;
        }
        return statusPayment.get(chatId);
    }

    public void removeStatusPayment(long chatId) {
        statusPayment.remove(chatId);
    }
}
