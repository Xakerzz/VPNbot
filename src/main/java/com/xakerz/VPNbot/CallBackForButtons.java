package com.xakerz.VPNbot;

public enum CallBackForButtons {

    ABOUT_VPN ("aboutVPN"),
    SUBSCRIPTION_FOR_VPN ("subForVPN"),
    SUBSCRIPTION_FOR_VPN_FROM_ABOUT_VPN ("sub_fom_about_vpn"),
    BACK_TO_MAIN_MENU ("backToMainMenu"),
    BACK_TO_ABOUT_VPN ("back_to_about_vpn"),
    ONE_MONTH ("one_month"),
    THREE_MONTHS ("three_month"),
    SIX_MONTHS ("six_months"),
    TWELVE_MONTHS ("twelve_months"),
    GET_OWN_VPN_SERVER ("get_ovn_vpn_server"),
    BACK_TO_MONTH_MENU ("back_to_month_menu"),
    PAY_FOR_ONE_MONTH ("pay_for_one_month"),
    PAY_FOR_THREE_MONTH ("pay_for_three_months"),
    PAY_FOR_SIX_MONTH ("pay_for_six_months"),
    PAY_FOR_TWELVE_MONTH ("pay_for_twelve_months"),
    PAY_FOR_OWN_SERVER ("pay_for_own_server"),

    PAID_FOR_ONE_MONTH ("paid_for_one_month"),
    SUCCESS ("success"),
    NOT_SUCCESS ("not_success");

    private String title;

    CallBackForButtons(String title) {
        this.title = title;
    }

    public String getCallBack() {
        return title;
    }

    @Override
    public String toString() {
        return
                "phrase ='" + title + '\'' +
                        '}';
    }
}
