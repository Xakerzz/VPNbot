package com.xakerz.VPNbot;

public enum BotButtons {
    BACK ("⬅\uFE0FНазад"),
    GET_SUBSCRIPTION ("Подписка на VPN\uD83D\uDEDC"),
    ABOUT_VPN ("❓Узнать о VPN❓"),
    ONE_MONTH ("1 - 220₽ ( ̶3̶0̶0̶₽)\uD83D\uDC4D"),
    THREE_MONTHS ("3 - 600₽ (6̶6̶0̶₽)\uD83D\uDE0E"),
    SIX_MONTHS ("6 - 1080₽ (̶1̶2̶0̶0₽)\uD83D\uDE07"),
    TWELVE_MONTH ("12 - 1800₽ (̶2̶1̶6̶0̶₽)\uD83E\uDD79"),
    PAY_FOR_ONE_MONTH ("Оплатить 220₽ \uD83D\uDCB3"),
    PAY_FOR_THREE_MONTH ("Оплатить 600₽ \uD83D\uDCB3"),
    PAY_FOR_SIX_MONTH ("Оплатить 1080₽ \uD83D\uDCB3"),
    PAY_FOR_TWELVE_MONTH ("Оплатить 1800₽ \uD83D\uDCB3"),
    GET_OWN_VPN_SERVER ("Заказать свой собственный сервер✅"),
    ORDER_OWN_VPN_SERVER ("Заказать сервер✅"),
    I_PAID ("Я оплатил \uD83D\uDC4D"),
    BUTTON_NOT_PAYMENT ("⛔\uFE0FОплата не поступила⛔\uFE0F"),
    BUTTON_PAYMENT ("✅Оплата поступила✅");

    private String title;


    BotButtons(String title) {
        this.title = title;
    }

    public String getTextButton() {
        return title;
    }

    @Override
    public String toString() {
        return
                "phrase ='" + title + '\'' +
                        '}';
    }
}
