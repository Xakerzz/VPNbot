package com.xakerz.VPNbot;

public enum Links {
    WELCOME_PHOTO ("https://disk.yandex.ru/i/6eLAX56pX7UIag"),
    LINK_FOR_PAY ("https://www.tinkoff.ru/rm/pantyukhov.roman1/4iJhb6234");

    private String title;

    Links(String title) {
        this.title = title;
    }

    public String getLink() {
        return title;
    }

    @Override
    public String toString() {
        return
                "phrase ='" + title + '\'' +
                        '}';
    }
}
