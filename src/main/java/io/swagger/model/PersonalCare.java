package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Applicable item types when category is Personal Care
 */

@JsonTypeName("PersonalCare")
public enum PersonalCare implements OneOfInventoryItemDetailsPayloadItemType{
    SHAMPOO("Shampoo"),
    SOAP("Soap"),
    DEODORANT("Deodorant");

    private String value;

    PersonalCare(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static PersonalCare fromValue(String text) {
        for (PersonalCare b : PersonalCare.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}