package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.model.ItemDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.*;

/**
 * Request payload to add new items
 */
@Schema(description = "Request payload to add new items", requiredProperties = {"categoryName", "itemType", "itemDetails" })
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = InventoryItemDetailsPayloadDeserializer.class)
public class InventoryItemDetailsPayload   {
  /**
   * category under which items can be classified
   */
  public enum CategoryNameEnum {
    DAIRY("Dairy"),

    GRAINS_AND_CEREALS("Grains and Cereals"),

    SNACKS("Snacks"),

    BEVERAGES("Beverages"),

    CANDY_AND_SWEETS("Candy and Sweets"),

    HOUSEHOLD_AND_CLEANING("Household and Cleaning"),

    PERSONAL_CARE("Personal Care"),

    OTHERS("Others");

    private String value;

    CategoryNameEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static CategoryNameEnum fromValue(String text) {
      for (CategoryNameEnum b : CategoryNameEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("categoryName")
//  @NotNull(message = "Category name cannot be null")
  private CategoryNameEnum categoryName = null;

  @JsonProperty("itemType")
//  @NotNull(message = "Item type cannot be null")
  private OneOfInventoryItemDetailsPayloadItemType itemType = null;

  @JsonProperty("itemDetails")
  @Valid
  private List<ItemDetails> itemDetails = new ArrayList<ItemDetails>();

  public InventoryItemDetailsPayload categoryName(CategoryNameEnum categoryName) {
    this.categoryName = categoryName;
    return this;
  }

  /**
   * category under which items can be classified
   * @return categoryName
   **/
  @Schema(example = "Beverages", description = "category under which items can be classified")
  @NotNull(message = "Category name is required")
  public CategoryNameEnum getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(CategoryNameEnum categoryName) {
    this.categoryName = categoryName;
  }

  public InventoryItemDetailsPayload itemType(OneOfInventoryItemDetailsPayloadItemType itemType) {
    this.itemType = itemType;
    return this;
  }

  /**
   * Item type under which items can be added
   * @return itemType
   **/
  @Schema(example = "Coffee", description = "Item type under which items can be added")
  @NotNull(message = "Item type is required")
  public OneOfInventoryItemDetailsPayloadItemType getItemType() {
    return itemType;
  }

  public void setItemType(OneOfInventoryItemDetailsPayloadItemType itemType) {
    this.itemType = itemType;
  }

  public InventoryItemDetailsPayload itemDetails(List<ItemDetails> itemDetails) {
    this.itemDetails = itemDetails;
    return this;
  }

  public InventoryItemDetailsPayload addItemDetailsItem(ItemDetails itemDetailsItem) {
    this.itemDetails.add(itemDetailsItem);
    return this;
  }

  /**
   * Details of items to be added
   * @return itemDetails
   **/
  @Schema(description = "Details of items to be added")
  @NotNull(message = "Item details are required")
  @Valid
  public List<ItemDetails> getItemDetails() {
    return itemDetails;
  }

  public void setItemDetails(List<ItemDetails> itemDetails) {
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
    InventoryItemDetailsPayload inventoryItemDetailsPayload = (InventoryItemDetailsPayload) o;
    return Objects.equals(this.categoryName, inventoryItemDetailsPayload.categoryName) &&
            Objects.equals(this.itemType, inventoryItemDetailsPayload.itemType) &&
            Objects.equals(this.itemDetails, inventoryItemDetailsPayload.itemDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categoryName, itemType, itemDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InventoryItemDetailsPayload {\n");

    sb.append("    categoryName: ").append(toIndentedString(categoryName)).append("\n");
    sb.append("    itemType: ").append(toIndentedString(itemType)).append("\n");
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