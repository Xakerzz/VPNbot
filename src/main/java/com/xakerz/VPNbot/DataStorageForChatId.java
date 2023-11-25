package com.xakerz.VPNbot;

import java.util.HashMap;
import java.util.Map;

public class DataStorageForChatId {
    private static final DataStorageForChatId instance = new DataStorageForChatId();
    private static final Map<String, Long> infoAboutUser = new HashMap<>();

    private DataStorageForChatId() {
    }

    public static DataStorageForChatId getInstance() {
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
