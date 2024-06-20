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
 * Applicable item types when category is Household And Cleaning
 */

@JsonTypeName("HouseHoldAndCleaning")
public enum HouseHoldAndCleaning implements OneOfInventoryItemDetailsPayloadItemType{
    CLEANING_SUPPLIES("Cleaning supplies"),
    TRASH_BAGS("Trash bags");

    private String value;

    HouseHoldAndCleaning(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static HouseHoldAndCleaning fromValue(String text) {
        for (HouseHoldAndCleaning b : HouseHoldAndCleaning.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
