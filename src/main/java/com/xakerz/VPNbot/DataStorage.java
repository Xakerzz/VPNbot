package com.xakerz.VPNbot;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static final DataStorage instance = new DataStorage();
    private final Map<Long, String> infoAboutUser = new HashMap<>();

    private DataStorage() {
    }

    public static DataStorage getInstance() {
        return instance;
    }

    public void setInfoAboutUser(long chatId, String userNameFirstName) {
        infoAboutUser.put(chatId, userNameFirstName);
    }

    public String getInfoAboutUser(long chatId) {
        return infoAboutUser.get(chatId);
    }

    public void removeInfoAboutUser(long chatId) {
        infoAboutUser.remove(chatId);
    }

    public String getCountUsers () {
        return String.valueOf(infoAboutUser.size());
    }
}
