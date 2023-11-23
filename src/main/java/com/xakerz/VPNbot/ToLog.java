package com.xakerz.VPNbot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToLog {
    private static final String LOG_FILE = "src/main/resources/Files/Log.txt";

    public static void log(String message, Long chatId, String userNameFirstName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.println(timeStamp + "  " + userNameFirstName + " (" + chatId + ")" + " - " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
