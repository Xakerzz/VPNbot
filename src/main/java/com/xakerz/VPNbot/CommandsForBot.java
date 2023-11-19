package com.xakerz.VPNbot;

public enum CommandsForBot {

    START ("/start"),
    KEYBOARD ("keyboard"),
    GET_LIST_CLIENTS ("✳\uFE0FПолучить список клиентов✳\uFE0F"),
    GET_STATISTIC ("✅Статистика✅"),
    MARK_SS ("ss://"),
    MARK_SPACE ("   "),
    GET_CLIENT_BASE ("♻\uFE0FПолучить базу пользователей♻\uFE0F"),
    STATISTIC ("✅Статистика✅");

    private String title;

    CommandsForBot(String title) {
        this.title = title;
    }

    public String getCommand() {
        return title;
    }

    @Override
    public String toString() {
        return
                "phrase ='" + title + '\'' +
                        '}';
    }
}
