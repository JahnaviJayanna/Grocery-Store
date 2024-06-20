package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ItemDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Details of items sold
 */
@Schema(description = "Details of items sold", requiredProperties = {"itemId","ItemDetails"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:21:22.303577991Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ItemDetailsWithId extends ItemDetails  {
  @JsonProperty("itemId")
  private String itemId = null;

  public ItemDetailsWithId itemId(String itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * System generated id for added item
   * @return itemId
   **/
  @Schema(example = "GS2736", description = "System generated id for added item")
  @NotNull

  @Pattern(regexp="^[A-Z]{2}\\d{4}$") @Size(min=3,max=10)   public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemDetailsWithId itemDetailsWithId = (ItemDetailsWithId) o;
    return Objects.equals(this.itemId, itemDetailsWithId.itemId) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemDetailsWithId {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
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