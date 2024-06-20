package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.ResponseParams;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * DeleteInventorySuccess
 */
@Schema(description = "Response for delete success", requiredProperties = {"itemId"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DeleteInventorySuccess extends ResponseParams  {
  @JsonProperty("itemId")
  private String itemId = null;

  public DeleteInventorySuccess itemId(String itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Item id for deleted item
   * @return itemId
   **/
  @Schema(example = "GS2736",description = "Item id for deleted item")
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
    DeleteInventorySuccess deleteInventorySuccess = (DeleteInventorySuccess) o;
    return Objects.equals(this.itemId, deleteInventorySuccess.itemId) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteInventorySuccess {\n");
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