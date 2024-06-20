package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Name and Id of item added
 */
@Schema(description = "Name and Id of item added", requiredProperties = {"itemName", "itemId"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ItemDetail   {
  @JsonProperty("itemId")
  private String itemId = null;

  @JsonProperty("itemName")
  private String itemName = null;

  @JsonProperty("createdOn")
  private String createdOn = null;

  @JsonProperty("modifiedOn")
  private String modifiedOn = null;

  public ItemDetail itemId(String itemId) {
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

  public ItemDetail itemName(String itemName) {
    this.itemName = itemName;
    return this;
  }

  /**
   * Name of item which needs to be added
   * @return itemName
   **/
  @Schema(example = "Nescafe", description = "Name of item which needs to be added")
      @NotNull

  @Size(min=1,max=80)   public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public ItemDetail createdOn(String createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  /**
   * The timestamp when the item is added
   * @return createdOn
   **/
  @Schema(example = "2024-05-14T18:48:21", description = "The timestamp when the item is added")
  
    public String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }

  public ItemDetail modifiedOn(String modifiedOn) {
    this.modifiedOn = modifiedOn;
    return this;
  }

  /**
   * The timestamp when the item is updated
   * @return modifiedOn
   **/
  @Schema(example = "2024-05-14T18:48:21", description = "The timestamp when the item is updated")
  
    public String getModifiedOn() {
    return modifiedOn;
  }

  public void setModifiedOn(String modifiedOn) {
    this.modifiedOn = modifiedOn;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemDetail itemDetail = (ItemDetail) o;
    return Objects.equals(this.itemId, itemDetail.itemId) &&
        Objects.equals(this.itemName, itemDetail.itemName) &&
        Objects.equals(this.createdOn, itemDetail.createdOn) &&
        Objects.equals(this.modifiedOn, itemDetail.modifiedOn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, itemName, createdOn, modifiedOn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemDetail {\n");
    
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    createdOn: ").append(toIndentedString(createdOn)).append("\n");
    sb.append("    modifiedOn: ").append(toIndentedString(modifiedOn)).append("\n");
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
