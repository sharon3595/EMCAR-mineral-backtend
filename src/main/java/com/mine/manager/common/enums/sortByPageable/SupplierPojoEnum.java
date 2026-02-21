package com.mine.manager.common.enums.sortByPageable;

import com.mine.manager.exception.PropertyNotFoundException;

import java.util.HashMap;
import java.util.Map;

public enum SupplierPojoEnum {

  id("id"),
  active("active"),
  createdBy("createdBy"),
  updatedBy("updatedBy"),
  createdAt("createdAt"),
  updatedAt("updatedAt"),
  firstName("name"),
  paternalSurname("surname"),
  documentNumber("documentNumber"),
  address("address"),
  personGroup("personGroup");

  private final String value;
  private static final Map<String, SupplierPojoEnum> lookup = new HashMap<>();

  static {
    for (SupplierPojoEnum d : SupplierPojoEnum.values()) {
      lookup.put(d.getValue(), d);
    }
  }

  SupplierPojoEnum(String value) {
    this.value = value;
  }

  private String getValue() {
    return value;
  }

  public static SupplierPojoEnum get(int value) {
    return lookup.get(value);
  }

  public static String fromString(String text) {
    if (text != null) {
      for (SupplierPojoEnum type : SupplierPojoEnum.values()) {
        if (text.equalsIgnoreCase(type.name())) {
          return type.getValue();
        }
      }
    }
    throw new PropertyNotFoundException(text, "Cliente");
  }
}
