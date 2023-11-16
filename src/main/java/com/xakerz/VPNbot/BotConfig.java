package com.xakerz.VPNbot;

public class BotConfig {
    static private final String BOT_NAME = "TrrustvpnBot";
    static private final String BOT_TOKEN = "6786415701:AAH8Jen0kQIHh1sAwQ2ZOJXVJWLywpuo7Dk";

    static private final  long CHAT_ID_CHANEL = -1002123748204L;
    static private final  long CHAT_ID_PAYMENT_CHANEL = -1002133393179L;
    static private final  long CHAT_ID_OTHER_CONTENT_CHANEL = -1002133393179L;
    public static long getCHAT_ID_OTHER_CONTENT_CHANE () {
        return CHAT_ID_OTHER_CONTENT_CHANEL;
    }
    public static long getCHAT_ID_PAYMENT_CHANEL () {
        return CHAT_ID_PAYMENT_CHANEL;
    }

    public static long getCHAT_ID_CHANEL () {
        return CHAT_ID_CHANEL;
    }

    public static String getBOT_NAME() {
        return BOT_NAME;
    }

    public static String getBOT_TOKEN() {
        return BOT_TOKEN;
    }
}
