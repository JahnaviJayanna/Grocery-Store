package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * response body for updating inventory details
 */
@Schema(description = "response body for updating inventory details", requiredProperties = {"items"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AddUpdateInventorySuccess extends ResponseParams  {
  @JsonProperty("items")
  @Valid
  private List<ItemDetail> items = new ArrayList<ItemDetail>();

  public AddUpdateInventorySuccess items(List<ItemDetail> items) {
    this.items = items;
    return this;
  }

  public AddUpdateInventorySuccess addItemsItem(ItemDetail itemsItem) {
    this.items.add(itemsItem);
    return this;
  }

  /**
   * List of items added to inventory
   * @return items
   **/
  @Schema(description = "List of items added to inventory")
  @NotNull
  @Valid
  public List<ItemDetail> getItems() {
    return items;
  }

  public void setItems(List<ItemDetail> items) {
    this.items = items;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddUpdateInventorySuccess addUpdateInventorySuccess = (AddUpdateInventorySuccess) o;
    return Objects.equals(this.items, addUpdateInventorySuccess.items) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddUpdateInventorySuccess {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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