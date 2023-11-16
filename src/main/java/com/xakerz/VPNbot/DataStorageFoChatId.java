package com.xakerz.VPNbot;

import java.util.HashMap;
import java.util.Map;

public class DataStorageFoChatId {
    private static final DataStorageFoChatId instance = new DataStorageFoChatId();
    private static final Map<String, Long> infoAboutUser = new HashMap<>();

    private DataStorageFoChatId() {
    }

    public static DataStorageFoChatId getInstance() {
        return instance;
    }

    public void setChatId(String userName, long chatId) {
        infoAboutUser.put(userName, chatId);
    }

    public Long getChatId(String userName) {
        return infoAboutUser.get(userName);
    }

    public void removeChatId(long chatId) {
        infoAboutUser.remove(chatId);
    }

    public String getCountUsers () {
        return String.valueOf(infoAboutUser.size());
    }
}
