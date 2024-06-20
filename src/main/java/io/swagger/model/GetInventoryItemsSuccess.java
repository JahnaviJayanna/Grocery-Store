package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.GetInventoryItemsSuccessInventoryList;
import io.swagger.model.ResponseParams;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.*;

/**
 * Response of inventory items list
 */
@Schema(description = "Response of inventory items list", requiredProperties = {"inventoryList"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GetInventoryItemsSuccess extends ResponseParams  {
  @JsonProperty("inventoryList")
  @Valid
  private List<GetInventoryItemsSuccessInventoryList> inventoryList = new ArrayList<GetInventoryItemsSuccessInventoryList>();

  public GetInventoryItemsSuccess inventoryList(List<GetInventoryItemsSuccessInventoryList> inventoryList) {
    this.inventoryList = inventoryList;
    return this;
  }

  public GetInventoryItemsSuccess addInventoryListItem(GetInventoryItemsSuccessInventoryList inventoryListItem) {
    this.inventoryList.add(inventoryListItem);
    return this;
  }

  /**
   * List of items present in inventory
   * @return inventoryList
   **/
  @Schema( description = "List of items present in inventory")
  @NotNull
  @Valid
  public List<GetInventoryItemsSuccessInventoryList> getInventoryList() {
    return inventoryList;
  }

  public void setInventoryList(List<GetInventoryItemsSuccessInventoryList> inventoryList) {
    this.inventoryList = inventoryList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetInventoryItemsSuccess getInventoryItemsSuccess = (GetInventoryItemsSuccess) o;
    return Objects.equals(this.inventoryList, getInventoryItemsSuccess.inventoryList) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inventoryList, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetInventoryItemsSuccess {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    inventoryList: ").append(toIndentedString(inventoryList)).append("\n");
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