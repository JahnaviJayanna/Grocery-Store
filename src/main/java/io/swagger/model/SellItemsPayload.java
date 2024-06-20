package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.SellItemsPayloadItems;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SellItemsPayload
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-29T17:15:54.887590953Z[GMT]")


public class SellItemsPayload   {
  @JsonProperty("items")
  @Valid
  private List<SellItemsPayloadItems> items = null;

  public SellItemsPayload items(List<SellItemsPayloadItems> items) {
    this.items = items;
    return this;
  }

  public SellItemsPayload addItemsItem(SellItemsPayloadItems itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<SellItemsPayloadItems>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Get items
   * @return items
   **/
  @Schema(description = "")
  @NotNull
  @Valid
  public List<SellItemsPayloadItems> getItems() {
    return items;
  }

  public void setItems(List<SellItemsPayloadItems> items) {
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
    SellItemsPayload sellItemsPayload = (SellItemsPayload) o;
    return Objects.equals(this.items, sellItemsPayload.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SellItemsPayload {\n");

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