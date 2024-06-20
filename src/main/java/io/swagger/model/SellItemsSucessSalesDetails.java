package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

/**
 * List items sold with amount
 */
@Schema(description = "List items sold with amount")
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class SellItemsSucessSalesDetails   {
  @JsonProperty("itemsList")
  @Valid
  private List<ItemDetailsWithId> itemsList = null;

  @JsonProperty("totalAmount")
  private Float totalAmount = null;

  public SellItemsSucessSalesDetails itemsList(List<ItemDetailsWithId> itemsList) {
    this.itemsList = itemsList;
    return this;
  }

  public SellItemsSucessSalesDetails addItemsListItem(ItemDetailsWithId itemsListItem) {
    if (this.itemsList == null) {
      this.itemsList = new ArrayList<ItemDetailsWithId>();
    }
    this.itemsList.add(itemsListItem);
    return this;
  }

  /**
   * Get itemsList
   * @return itemsList
   **/
  @Schema(description = "")
      @Valid
    public List<ItemDetailsWithId> getItemsList() {
    return itemsList;
  }

  public void setItemsList(List<ItemDetailsWithId> itemsList) {
    this.itemsList = itemsList;
  }

  public SellItemsSucessSalesDetails totalAmount(Float totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  /**
   * Total amount of items sold
   * @return totalAmount
   **/
  @Schema(description = "Total amount of items sold")
  
    public Float getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Float totalAmount) {
    this.totalAmount = totalAmount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SellItemsSucessSalesDetails sellItemsSucessSalesDetails = (SellItemsSucessSalesDetails) o;
    return Objects.equals(this.itemsList, sellItemsSucessSalesDetails.itemsList) &&
        Objects.equals(this.totalAmount, sellItemsSucessSalesDetails.totalAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemsList, totalAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SellItemsSucessSalesDetails {\n");
    
    sb.append("    itemsList: ").append(toIndentedString(itemsList)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
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
