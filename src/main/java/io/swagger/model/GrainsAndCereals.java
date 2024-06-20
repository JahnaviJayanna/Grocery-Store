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
 * Applicable item types when category is Grains And Cereals
 */
@JsonTypeName("GrainsAndCereals")
public enum GrainsAndCereals implements OneOfInventoryItemDetailsPayloadItemType {
    WHITE_RICE("White rice"),
    BROWN_RICE("Brown Rice"),
    BASMATI_RICE("Basmati rice"),
    WHEAT("Wheat"),
    MILLET("Millet"),
    CHICKPEAS("Chickpeas"),
    MUNG_BEANS("Mung Beans");

    private String value;

    GrainsAndCereals(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static GrainsAndCereals fromValue(String text) {
        for (GrainsAndCereals b : GrainsAndCereals.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}