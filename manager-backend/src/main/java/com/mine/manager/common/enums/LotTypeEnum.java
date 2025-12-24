package com.mine.manager.common.enums;

import com.mine.manager.exception.PropertyNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LotTypeEnum {

    RECEIPT("Recibo"),
    RECEPTION("Recepción");

    private final String value;

    public static String fromString(String text) {
        if (text != null) {
            for (LotTypeEnum type : LotTypeEnum.values()) {
                if (text.equalsIgnoreCase(type.getValue())) {
                    return type.getValue();
                }
            }
        }
        throw new PropertyNotFoundException(text, "Asignado");
    }
}
