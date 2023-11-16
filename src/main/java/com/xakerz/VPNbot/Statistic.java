package com.xakerz.VPNbot;

public class Statistic {

    static private int amountUsers;
    static private int amountUsersEntry;
    static private int pressBackButton;
    static private int pressAboutVPNButton;
    static private int pressSubFromAboutVPNButton;
    static private int pressSubButton;
    static private int pressSubOneMonthButton;
    static private int pressSubThreeMonthsButton;
    static private int pressSubSixMonthsButton;
    static private int pressSubTwelveMonthsButton;
    static private int pressGetOwnServerButton;
    static private int pressPaidOneMonthButton;
    static private int pressPaidThreeMonthsButton;
    static private int pressPaidSixMonthsButton;
    static private int pressPaidTwelveMonthsButton;
    static private int successPaymentCounter;
    static private int notSuccessPaymentCounter;


    public static int getAmountUsers() {
        return DataStorage.getInstance().getCountUsers();
    }

    public static void setAmountUsers() {
        Statistic.amountUsers++;
    }

    public static int getAmountUsersEntry() {
        return amountUsersEntry;
    }

    public static void setAmountUsersEntry() {
        Statistic.amountUsersEntry++;
    }

    public static int getPressBackButton() {
        return pressBackButton;
    }

    public static void setPressBackButton() {
        Statistic.pressBackButton++;
    }

    public static int getPressAboutVPNButton() {
        return pressAboutVPNButton;
    }

    public static void setPressAboutVPNButton() {
        Statistic.pressAboutVPNButton++;
    }

    public static int getPressSubFromAboutVPNButton() {
        return pressSubFromAboutVPNButton;
    }

    public static void setPressSubFromAboutVPNButton() {
        Statistic.pressSubFromAboutVPNButton++;
    }

    public static int getPressSubButton() {
        return pressSubButton;
    }

    public static void setPressSubButton() {
        Statistic.pressSubButton++;
    }

    public static int getPressSubOneMonthButton() {
        return pressSubOneMonthButton;
    }

    public static void setPressSubOneMonthButton() {
        Statistic.pressSubOneMonthButton++;
    }

    public static int getPressSubThreeMonthsButton() {
        return pressSubThreeMonthsButton;
    }

    public static void setPressSubThreeMonthsButton() {
        Statistic.pressSubThreeMonthsButton++;
    }

    public static int getPressSubSixMonthsButton() {
        return pressSubSixMonthsButton;
    }

    public static void setPressSubSixMonthsButton() {
        Statistic.pressSubSixMonthsButton++;
    }

    public static int getPressSubTwelveMonthsButton() {
        return pressSubTwelveMonthsButton;
    }

    public static void setPressSubTwelveMonthsButton() {
        Statistic.pressSubTwelveMonthsButton++;
    }

    public static int getPressGetOwnServerButton() {
        return pressGetOwnServerButton;
    }

    public static void setPressGetOwnServerButton() {
        Statistic.pressGetOwnServerButton++;
    }

    public static int getPressPaidOneMonthButton() {
        return pressPaidOneMonthButton;
    }

    public static void setPressPaidOneMonthButton() {
        Statistic.pressPaidOneMonthButton++;
    }

    public static int getPressPaidThreeMonthsButton() {
        return pressPaidThreeMonthsButton;
    }

    public static void setPressPaidThreeMonthsButton() {
        Statistic.pressPaidThreeMonthsButton++;
    }

    public static int getPressPaidSixMonthsButton() {
        return pressPaidSixMonthsButton;
    }

    public static void setPressPaidSixMonthsButton() {
        Statistic.pressPaidSixMonthsButton++;
    }

    public static int getPressPaidTwelveMonthsButton() {
        return pressPaidTwelveMonthsButton;
    }

    public static void setPressPaidTwelveMonthsButton() {
        Statistic.pressPaidTwelveMonthsButton++;
    }

    public static int getSuccessPaymentCounter() {
        return successPaymentCounter;
    }

    public static void setSuccessPaymentCounter() {
        Statistic.successPaymentCounter++;
    }

    public static int getNotSuccessPaymentCounter() {
        return notSuccessPaymentCounter;
    }

    public static void setNotSuccessPaymentCounter() {
        Statistic.notSuccessPaymentCounter++;
    }

    public String toString() {
        return String.format("Всего входов в бота: %d\n" +
                        "Количество участников: %d\n" +
                        "Количество переходов в раздел о VPN: %d\n" +
                        "Количество нажатий кнопки назад: %d\n" +
                        "Переходов в раздел выбора подписок: %d\n" +
                        "Переходов в раздел выбора подписок из раздела о VPN: %d\n\n" +
                        "Количество переходов в подписку\n на 1 месяц: %d\n\n" +
                        "Количество переходов в подписку\n на 3 месяца: %d\n\n" +
                        "Количество переходов в подписку\n на 6 месяцев: %d\n\n" +
                        "Количество переходов в подписку\n на 12 месяцев: %d\n\n" +
                        "Количество переходов в раздел\n <свой сервер>: %d\n\n" +
                        "Нажата кнопка <я оплатил> в подписке\n на 1 месяц: %d\n\n" +
                        "Нажата кнопка <я оплатил> в подписке\n на 3 месяца: %d\n\n" +
                        "Нажата кнопка <я оплатил> в подписке\n на 6 месяцев: %d\n\n" +
                        "Нажата кнопка <я оплатил> в подписке\n на 12 месяцев: %d\n\n" +
                        "Количество успешных продаж: %d\n" +
                        "Количество неуспешных продаж: %d", getAmountUsersEntry(), getAmountUsers(), getPressAboutVPNButton(),
                getPressBackButton(), getPressSubButton(), getPressSubFromAboutVPNButton(), getPressSubOneMonthButton(), getPressSubThreeMonthsButton(),
                getPressSubSixMonthsButton(), getPressSubTwelveMonthsButton(), getPressGetOwnServerButton(), getPressPaidOneMonthButton(),
                getPressPaidThreeMonthsButton(), getPressPaidSixMonthsButton(), getPressPaidTwelveMonthsButton(), getSuccessPaymentCounter(), getNotSuccessPaymentCounter());
    }
}
