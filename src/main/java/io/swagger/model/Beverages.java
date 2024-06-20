package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Applicable item types when category is Beverages
 */

@JsonTypeName("Beverages")
public enum Beverages implements OneOfInventoryItemDetailsPayloadItemType{
    SOFT_DRINKS("Soft drinks"),
    JUICE("Juice"),
    WATER("Water"),
    TEA("Tea"),
    COFFEE("Coffee");

    private String value;

    Beverages(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Beverages fromValue(String text) {
        for (Beverages b : Beverages.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}