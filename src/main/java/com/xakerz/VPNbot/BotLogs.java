package com.xakerz.VPNbot;

public enum BotLogs {
    ENTER_IN_THE_ONE_MONTH_SUB("Выбрал подписку на 1 месяц."),
    TO_SUB_FROM_ABOUT_VPN("Зашел в раздел подписки из раздела о VPN."),
    ENTER_IN_THE_BOT("Зашел в бота."),
    ENTER_IN_ABOUT_VPN ("Зашел в раздел о VPN."),
    BACK_TO_ABOUT_VPN ("Вернулся в раздел о VPN."),
    BACK_TO_MAIN_MENU ("Вернулся в главное меню."),
    ENTER_SUB_MENU ("Зашел в раздел выбора подписки."),
    ENTER_IN_THE_THREE_MONTHS_SUB ("Выбрал подписку на 3 месяца."),
    ENTER_IN_THE_SIX_MONTHS_SUB ("Выбрал подписку на 6 месяцев."),
    ENTER_IN_THE_TWELVE_MONTHS_SUB ("Выбрал подписку на 12 месяцев."),
    ENTER_IN_GET_OWN_SERVER_MENU ("В заказе заказа своего сервера."),
    BACK_TO_SUB_MENU_FROM_MONTH_MENU ("Вернулся в раздел подписок из выбранной подписки."),
    PRESS_PAY_BUTTON_ONE_MONTH ("Нажал кнопку оплаты подписки на 1 месяц."),
    PRESS_PAY_BUTTON_THREE_MONTHS ("Нажал кнопку оплаты подписки на 3 месяца."),
    PRESS_PAY_BUTTON_SIX_MONTHS ("Нажал кнопку оплаты подписки на 6 месяцев."),
    PRESS_PAY_BUTTON_TWELVE_MONTHS ("Нажал кнопку оплаты подписки на 12 месяцев.");

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
