package com.xakerz.VPNbot;

public enum CommandsForBot {

    START ("/start"),
    STATISTIC ("Statistic 9764");

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
