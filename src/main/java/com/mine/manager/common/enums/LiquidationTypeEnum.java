package com.mine.manager.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mine.manager.exception.PropertyNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LiquidationTypeEnum {

    COOPERATIVE("Cooperativa"),
    PRIVATE("Particular");

    private final String value;

    @JsonCreator
    public static LiquidationTypeEnum fromString(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            return LiquidationTypeEnum.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        for (LiquidationTypeEnum type : LiquidationTypeEnum.values()) {
            if (type.getValue().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new PropertyNotFoundException(text, "Tipo de liquidación");
    }

    @JsonValue
    public String toValue() {
        return this.getValue();
    }
}
