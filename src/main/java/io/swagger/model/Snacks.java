package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;


import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Applicable item types when category is Snacks
 */

@JsonTypeName("Snacks")
public enum Snacks implements OneOfInventoryItemDetailsPayloadItemType{
    CHIPS("Chips"),
    CRACKERS("Crackers"),
    NUTS("Nuts"),
    POPCORN("Popcorn");

    private String value;

    Snacks(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Snacks fromValue(String text) {
        for (Snacks b : Snacks.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}