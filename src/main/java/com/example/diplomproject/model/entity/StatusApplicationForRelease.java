package com.example.diplomproject.model.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public enum StatusApplicationForRelease {
    IN_PROCESSING,
    ACCEPTED,
    AWAITING_PAYMENT,
    PAID,
    COMPLETED;

        private static final Map<StatusApplicationForRelease, String> russianNames = new HashMap<>();
        static {
            russianNames.put(IN_PROCESSING, "В обработке");
            russianNames.put(ACCEPTED, "Принят");
            russianNames.put(AWAITING_PAYMENT, "Ожидает оплаты");
            russianNames.put(PAID, "Оплачено");
            russianNames.put(COMPLETED, "Завершен");

        }


    public static Map<StatusApplicationForRelease, String> getRussianName() {
            return russianNames;
        }


}
