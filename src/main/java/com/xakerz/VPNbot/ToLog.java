package com.xakerz.VPNbot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToLog {
    private static final String LOG_FILE = Paths.get("").toAbsolutePath() + "/VPNbot/VPNbot/out/artifacts/VPNbot_jar/Log.txt";
    //private static final String LOG_FILE = "src/main/resources/Files/Log.txt";



    public static void log(Long chatId, String logMessage) {
        String[] infoArray = DataStorage.getInstance().getInfoAboutUser(chatId).trim().split(CommandsForBot.MARK_SPACE.getCommand());
        String firstName = infoArray[1];
        String userName = infoArray[0];

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.println(timeStamp + "  " + firstName + "  " + userName + " (" + chatId + ")" + " - " + logMessage);
            System.out.println(timeStamp + "  " + firstName + "  " + userName + " (" + chatId + ")" + " - " + logMessage);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
}
