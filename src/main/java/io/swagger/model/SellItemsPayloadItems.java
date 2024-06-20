package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Request body to sell items
 */
@Schema(description = "Request body to sell items", requiredProperties = {"itemId", "units"})
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-29T17:15:54.887590953Z[GMT]")


public class SellItemsPayloadItems   {
    @JsonProperty("itemId")
    private String itemId = null;

    @JsonProperty("units")
    private Integer units = null;

    public SellItemsPayloadItems itemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    /**
     * System generated id for added item
     * @return itemId
     **/
    @Schema(example = "ITM736", description = "System generated id for added item")
    @NotNull
    @NotBlank
    @Pattern(regexp="^[A-Z]{3}\\d{3}$") @Size(min=3,max=10)   public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public SellItemsPayloadItems units(Integer units) {
        this.units = units;
        return this;
    }

    /**
     * Number of units available for each item
     * @return units
     **/
    @Schema(example = "5", description = "Number of units available for each item")
    @NotNull
    @NotEmpty
    @NotBlank
    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SellItemsPayloadItems sellItemsPayloadItems = (SellItemsPayloadItems) o;
        return Objects.equals(this.itemId, sellItemsPayloadItems.itemId) &&
                Objects.equals(this.units, sellItemsPayloadItems.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, units);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SellItemsPayloadItems {\n");

        sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
        sb.append("    units: ").append(toIndentedString(units)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
