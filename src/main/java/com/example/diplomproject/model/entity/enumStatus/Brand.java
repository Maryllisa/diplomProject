package com.example.diplomproject.model.entity.enumStatus;

import java.util.HashMap;
import java.util.Map;

public enum Brand {
    VOLVO,
    SCANIA,
    MERCEDES_BENZ,
    MAN,
    DAF,
    IVECO,
    RENAULT,
    VOLKSWAGEN,
    TOYOTA,
    BMW,
    AUDI,
    FORD,
    HYUNDAI,
    NISSAN,
    CHEVROLET,
    KIA,
    SUZUKI;

    private static final Map<Brand, String> russianNames = new HashMap<>();
    static {
        russianNames.put(VOLVO, "Volvo");
        russianNames.put(SCANIA, "Scania");
        russianNames.put(MERCEDES_BENZ, "Mercedes benz");
        russianNames.put(MAN, "Man");
        russianNames.put(DAF, "Daf");
        russianNames.put(IVECO, "Iveco");
        russianNames.put(RENAULT, "Renault");
        russianNames.put(VOLKSWAGEN, "Volkswagen");
        russianNames.put(TOYOTA, "Toyota");
        russianNames.put(BMW, "BMW");
        russianNames.put(AUDI, "Audi");
        russianNames.put(FORD, "Ford");
        russianNames.put(HYUNDAI, "Hyundai");
        russianNames.put(NISSAN, "Nissan");
        russianNames.put(CHEVROLET, "Chevrolet");
        russianNames.put(KIA, "KIA");
        russianNames.put(SUZUKI, "Suzuki");
    }
    public static Map<Brand, String> getRussianName() {
        return russianNames;
    }
}
