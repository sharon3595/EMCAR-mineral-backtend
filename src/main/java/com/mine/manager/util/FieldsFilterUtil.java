package com.mine.manager.util;

import java.util.HashMap;
import java.util.Map;

public class FieldsFilterUtil {
  private final Map<String, Object> fields;

  public FieldsFilterUtil() {
    this.fields = new HashMap<>();
    this.fields.put("likeFields", new HashMap<>());
    this.fields.put("equalsFields", new HashMap<>());
    this.fields.put("dateFields", new HashMap<>());
    this.fields.put("inFields", new HashMap<>());
    this.fields.put("inSomeFields", new HashMap<>());
  }

  public void addLikeField(String fieldName, Object value) {
    ((Map<String, Object>)this.fields.get("likeFields")).put(fieldName, value);
  }

  public void addEqualsField(String fieldName, Object value) {
    ((Map<String, Object>)this.fields.get("equalsFields")).put(fieldName, value);
  }

  public void addDateField(String fieldName, Object value) {
    ((Map<String, Object>)this.fields.get("dateFields")).put(fieldName, value);
  }

  public void addInField(String fieldName, Object value) {
    ((Map<String, Object>)this.fields.get("inFields")).put(fieldName, value);
  }

  public void addInSomeField(String fieldName, Object value) {
    ((Map<String, Object>)this.fields.get("inSomeFields")).put(fieldName, value);
  }

  public void addDateField(String fieldName, Object startDate, Object endDate) {
    if (startDate != null) {
      ((Map<String, Object>)this.fields.get("dateFields")).put(fieldName + ".startDate", startDate);
    }
    if (endDate != null) {
      ((Map<String, Object>)this.fields.get("dateFields")).put(fieldName + ".endDate", endDate);
    }
  }

  public Map<String, Object> getFilterFields() {
    return this.fields;
  }
}
