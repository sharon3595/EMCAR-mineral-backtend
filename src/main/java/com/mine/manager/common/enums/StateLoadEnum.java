package com.mine.manager.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StateLoadEnum {

    PENDING("Pendiente"),
    LIQUIDATED("Liquidado"),
    INACTIVE("Eliminado");

    private final String value;
}
