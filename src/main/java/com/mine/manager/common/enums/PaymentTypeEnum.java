package com.mine.manager.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mine.manager.exception.PropertyNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentTypeEnum {


    CASH("Efectivo"),
    CHECK("Cheque");

    private final String value;

    @JsonCreator
    public static PaymentTypeEnum fromString(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            return PaymentTypeEnum.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        for (PaymentTypeEnum type : PaymentTypeEnum.values()) {
            if (type.getValue().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new PropertyNotFoundException(text, "Tipo de pago");
    }

    @JsonValue
    public String toValue() {
        return this.getValue();
    }

}
