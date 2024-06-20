package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.*;

import javax.validation.constraints.NotNull;


/**
 * Update item details present in inventory
 */
@Schema(description = "Update item details present in inventory")
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UpdateInventoryItemsPayload   {
  @JsonProperty("itemDetails")
  private ItemDetails itemDetails = null;

  public UpdateInventoryItemsPayload itemDetails(ItemDetails itemDetails) {
    this.itemDetails = itemDetails;
    return this;
  }

  /**
   * Get itemDetails
   * @return itemDetails
   **/
  @Schema(description = "")
  @NotNull(message = "Item details is required")
  @Valid
  public ItemDetails getItemDetails() {
    return itemDetails;
  }

  public void setItemDetails(ItemDetails itemDetails) {
    this.itemDetails = itemDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateInventoryItemsPayload updateInventoryItemsPayload = (UpdateInventoryItemsPayload) o;
    return Objects.equals(this.itemDetails, updateInventoryItemsPayload.itemDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateInventoryItemsPayload {\n");

    sb.append("    itemDetails: ").append(toIndentedString(itemDetails)).append("\n");
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
