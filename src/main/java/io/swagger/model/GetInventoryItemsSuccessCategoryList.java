package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ItemDetailsWithId;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

/**
 * GetInventoryItemsSuccessCategoryList
 */
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GetInventoryItemsSuccessCategoryList   {
  @JsonProperty("itemType")
  private String itemType = null;

  @JsonProperty("itemsList")
  @Valid
  private List<ItemDetailsWithId> itemsList = null;

  public GetInventoryItemsSuccessCategoryList itemType(String itemType) {
    this.itemType = itemType;
    return this;
  }

  /**
   * Type which item belongs
   * @return itemType
   **/
  @Schema(example = "Beverages", description = "Type which item belongs")
  
    public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public GetInventoryItemsSuccessCategoryList itemsList(List<ItemDetailsWithId> itemsList) {
    this.itemsList = itemsList;
    return this;
  }

  public GetInventoryItemsSuccessCategoryList addItemsListItem(ItemDetailsWithId itemsListItem) {
    if (this.itemsList == null) {
      this.itemsList = new ArrayList<ItemDetailsWithId>();
    }
    this.itemsList.add(itemsListItem);
    return this;
  }

  /**
   * List of item under specified category and name
   * @return itemsList
   **/
  @Schema(description = "List of item under specified category and name")
      @Valid
    public List<ItemDetailsWithId> getItemsList() {
    return itemsList;
  }

  public void setItemsList(List<ItemDetailsWithId> itemsList) {
    this.itemsList = itemsList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetInventoryItemsSuccessCategoryList getInventoryItemsSuccessCategoryList = (GetInventoryItemsSuccessCategoryList) o;
    return Objects.equals(this.itemType, getInventoryItemsSuccessCategoryList.itemType) &&
        Objects.equals(this.itemsList, getInventoryItemsSuccessCategoryList.itemsList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemType, itemsList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetInventoryItemsSuccessCategoryList {\n");
    
    sb.append("    itemType: ").append(toIndentedString(itemType)).append("\n");
    sb.append("    itemsList: ").append(toIndentedString(itemsList)).append("\n");
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
