package com.example.diplomproject.model.entity.marking;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum StatusMarkingApplication {
    PENDING,
    APPROVED,
    COMPLETED,
    CANCELED;

    private static final Map<StatusMarkingApplication, String> russianNames = new HashMap<>();
    static {
        russianNames.put(PENDING, "В ожидании");
        russianNames.put(APPROVED, "Одобрено");
        russianNames.put(COMPLETED, "Выполнено");
        russianNames.put(CANCELED, "Отказано");

    }
    public static Map<StatusMarkingApplication, String> getRussianName() {
        return russianNames;
    }

}