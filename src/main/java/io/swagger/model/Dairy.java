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
 * Applicable item types when category is Dairy
 */
@JsonTypeName("Dairy")
public enum Dairy implements OneOfInventoryItemDetailsPayloadItemType{
    MILK("Milk"),
    CHEESE("Cheese"),
    YOGURT("Yogurt"),
    BUTTER("Butter");

    private String value;

    Dairy(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Dairy fromValue(String text) {
        for (Dairy b : Dairy.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}