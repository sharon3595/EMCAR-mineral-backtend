package com.mine.manager.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mine.manager.exception.PropertyNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LotTypeEnum {

    RECEIPT("Recibo"),
    RECEPTION("Recepción");

    private final String value;

    @JsonCreator
    public static LotTypeEnum fromString(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            return LotTypeEnum.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        for (LotTypeEnum type : LotTypeEnum.values()) {
            if (type.getValue().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new PropertyNotFoundException(text, "Asignación");
    }

    @JsonValue
    public String toValue() {
        return this.getValue();
    }
}