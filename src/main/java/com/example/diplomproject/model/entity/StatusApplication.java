package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.entity.marking.StatusMarkingApplication;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public enum StatusApplication {
    PENDING, // В ожидании
    PROCESSING,// Обрабатывается
    COMPLETED, // Завершено
    CANCELLED; // Отменено
    private static final Map<StatusApplication, String> russianNames = new HashMap<>();
    static {
        russianNames.put(PENDING, "В ожидании");
        russianNames.put(PROCESSING, "Обрабатывается");
        russianNames.put(COMPLETED, "Завершено");
        russianNames.put(CANCELLED, "Отменено");

    }
    public static Map<StatusApplication, String> getRussianName() {
        return russianNames;
    }

}
