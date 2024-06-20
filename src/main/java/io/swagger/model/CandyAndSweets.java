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
 * Applicable item types when category is Candy And Sweets
 */
@JsonTypeName("CandyAndSweets")
public enum CandyAndSweets implements OneOfInventoryItemDetailsPayloadItemType{
    CHOCOLATE_BARS("Chocolate bars"),
    CANDY("Candy");

    private String value;

    CandyAndSweets(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CandyAndSweets fromValue(String text) {
        for (CandyAndSweets b : CandyAndSweets.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}