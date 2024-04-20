package com.example.diplomproject.model.entity.marking;


import com.example.diplomproject.model.entity.Brand;

import java.util.HashMap;
import java.util.Map;

// акцизными марками,
// контрольными (идентификационными) знаками,
// знаками с радиочастотной меткой (RFID-меткой),
// стикеровку,
// нанесение кодов DataMatrix.
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
        russianNames.put(STICKER, "знаками с радиочастотной меткой (RFID-меткой)");
        russianNames.put(RFID_MARK, "стикеровку");
        russianNames.put(DATAMATRIX_CODE, "нанесение кодов DataMatrix");

    }
    public static Map<TypeMarking, String> getRussianName() {
        return russianNames;
    }

}