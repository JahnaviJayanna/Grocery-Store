package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Details of items to be added
 */
@Schema(description = "Details of items to be added", requiredProperties = {"itemName", "price", "quantity", "units"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ItemDetails   {
  @JsonProperty("itemName")
  private String itemName = null;

  @JsonProperty("description")
  private String description = null;
  @JsonProperty("price")
  private Float price = null;

  @JsonProperty("quantity")
  private Float quantity = null;

  @JsonProperty("units")
  private Integer units = null;

  public ItemDetails itemName(String itemName) {
    this.itemName = itemName;
    return this;
  }

  /**
   * Name of item which needs to be added
   * @return itemName
   **/
  @Schema(example = "Nescafe", description = "Name of item which needs to be added")
  @NotNull(message = "Item name is required")
  @NotEmpty(message = "Item name is required")
  @Size(min=1,max=80)
  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public ItemDetails description(String itemNdescriptioname) {
    this.description = description;
    return this;
  }

  /**
   * Name of item which needs to be added
   * @return itemName
   **/
  @Schema(example = "Nescafe", description = "Name of item which needs to be added")
  @Size(max=100)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ItemDetails price(Float price) {
    this.price = price;
    return this;
  }


  /**
   * Price of item in INR
   * @return price
   **/
  @Schema(example = "45", description = "Price of item in INR")
  @NotNull(message = "price is required")
  @Min(value = 1, message = "price is less than minimum value")
  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public ItemDetails quantity(Float quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Quantity of item in terms of ltrs or kilograms
   * @return quantity
   **/
  @Schema(example = "0.5", description = "Quantity of item in terms of ltrs or kilograms")
  @NotNull(message = "Quantity is required")
  @DecimalMin(value = "0.05" , message = "Minimum quantity value is 0.05")
  public Float getQuantity() {
    return quantity;
  }

  public void setQuantity(Float quantity) {
    this.quantity = quantity;
  }

  public ItemDetails units(Integer units) {
    this.units = units;
    return this;
  }

  /**
   * Number of units available for each item
   * @return units
   **/
  @Schema(example = "5", description = "Number of units available for each item")
  @NotNull(message = "units is required")
  @Min(value = 1, message = "Minimum of 1 unit needs to be present to add item")
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
    ItemDetails itemDetails = (ItemDetails) o;
    return Objects.equals(this.itemName, itemDetails.itemName) &&
            Objects.equals(this.description, itemDetails.description)&&
            Objects.equals(this.price, itemDetails.price) &&
            Objects.equals(this.quantity, itemDetails.quantity) &&
            Objects.equals(this.units, itemDetails.units);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemName, price, quantity, units);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemDetails {\n");

    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
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