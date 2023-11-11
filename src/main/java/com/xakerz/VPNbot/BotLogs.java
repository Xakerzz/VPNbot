package com.xakerz.VPNbot;

public enum BotLogs {
    ENTER_IN_THE_ONE_MONTH_SUB("\nВыбрал подписку на 1 месяц"),
    TO_SUB_FROM_ABOUT_VPN("\nЗашел в раздел подписки из раздела о VPN"),
    ENTER_IN_THE_BOT("\nЗашел в бота"),
    ENTER_IN_ABOUT_VPN ("\nЗашел в раздел о VPN"),
    BACK_TO_ABOUT_VPN ("\nВернулся в раздел о VPN"),
    BACK_TO_MAIN_MENU ("\nВернулся в главное меню"),
    ENTER_SUB_MENU ("\nЗашел в раздел выбора подписки"),
    ENTER_IN_THE_THREE_MONTHS_SUB ("\nВыбрал подписку на 3 месяца"),
    ENTER_IN_THE_SIX_MONTHS_SUB ("\nВыбрал подписку на 6 месяцев"),
    ENTER_IN_THE_TWELVE_MONTHS_SUB ("\nВыбрал подписку на 12 месяцев"),
    ENTER_IN_GET_OWN_SERVER_MENU ("\nВ заказе заказа своего сервера"),
    BACK_TO_SUB_MENU_FROM_MONTH_MENU ("\nВернулся в раздел подписок из выбранной подписки"),
    PRESS_PAY_BUTTON_ONE_MONTH ("\nНажал кнопку оплаты подписки на один месяц");

    private String title;


    BotLogs(String title) {
        this.title = title;
    }

    public String getBotLog() {
        return title;
    }

    @Override
    public String toString() {
        return
                "botLog ='" + title + '\'' +
                        '}';
    }
}
