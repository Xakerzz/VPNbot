package com.xakerz.VPNbot;

import java.util.HashMap;
import java.util.Map;

public class StatusPayment {
    private static final StatusPayment instance = new StatusPayment();
    private final Map<Long, Boolean> statusPayment = new HashMap<>();

    private StatusPayment() {
    }

    public static StatusPayment getInstance() {
        return instance;
    }

    public void setStatusPayment(long chatId, Boolean statusPaymentBoolean) {
        statusPayment.put(chatId, statusPaymentBoolean);
    }

    public Boolean getStatusPayment(long chatId) {
        if (statusPayment.get(chatId) == null) {
            return false;
        }
        return statusPayment.get(chatId);
    }

    public void removeStatusPayment(long chatId) {
        statusPayment.remove(chatId);
    }
}
