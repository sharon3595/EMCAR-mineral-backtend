package com.mine.manager.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mine.manager.exception.PropertyNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentChanelEnum {


    CASHIER("Caja"),
    BANK("Banco");

    private final String value;

    @JsonCreator
    public static PaymentChanelEnum fromString(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            return PaymentChanelEnum.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        for (PaymentChanelEnum type : PaymentChanelEnum.values()) {
            if (type.getValue().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new PropertyNotFoundException(text, "Canal de pago");
    }

    @JsonValue
    public String toValue() {
        return this.getValue();
    }

}
