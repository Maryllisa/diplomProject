package com.example.diplomproject.model.entity.marking;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

// акцизными марками,
// контрольными (идентификационными) знаками,
// знаками с радиочастотной меткой (RFID-меткой),
// стикеровку,
// нанесение кодов DataMatrix.
@Getter
public enum TypeMarking {
    ACCISE_MARK,
    CONTROL_MARK,
    RFID_MARK,
    STICKER,
    DATAMATRIX_CODE;
    private static final Map<TypeMarking, String> russianNames = new HashMap<>();
    static {
        russianNames.put(ACCISE_MARK, "акцизными марками");
        russianNames.put(CONTROL_MARK, "контрольными (идентификационными) знаками");
        russianNames.put(RFID_MARK, "знаками с радиочастотной меткой (RFID-меткой)");
        russianNames.put(STICKER, "стикеровку");
        russianNames.put(DATAMATRIX_CODE, "нанесение кодов DataMatrix");

    }
    public static Map<TypeMarking, String> getRussianName() {
        return russianNames;
    }

}