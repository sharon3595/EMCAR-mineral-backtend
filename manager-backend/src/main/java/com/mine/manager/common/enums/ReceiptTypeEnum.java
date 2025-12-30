package com.mine.manager.common.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mine.manager.exception.PropertyNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceiptTypeEnum {

    MANUAL("Manual"),
    SYSTEM("Sistema");

    private final String value;

    @JsonCreator
    public static ReceiptTypeEnum fromString(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            return ReceiptTypeEnum.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        for (ReceiptTypeEnum type : ReceiptTypeEnum.values()) {
            if (type.getValue().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new PropertyNotFoundException(text, "Tipo recibo");
    }

    @JsonValue
    public String toValue() {
        return this.getValue();
    }
}