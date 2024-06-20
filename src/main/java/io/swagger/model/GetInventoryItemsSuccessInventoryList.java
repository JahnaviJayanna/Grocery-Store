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
 * GetInventoryItemsSuccessInventoryList
 */
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GetInventoryItemsSuccessInventoryList   {
  @JsonProperty("categoryName")
  private String categoryName = null;

  @JsonProperty("categoryList")
  @Valid
  private List<GetInventoryItemsSuccessCategoryList> categoryList = null;

  public GetInventoryItemsSuccessInventoryList categoryName(String categoryName) {
    this.categoryName = categoryName;
    return this;
  }

  /**
   * Category of which item belongs
   * @return categoryName
   **/
  @Schema(example = "Beverages", description = "Category of which item belongs")
  
    public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public GetInventoryItemsSuccessInventoryList categoryList(List<GetInventoryItemsSuccessCategoryList> categoryList) {
    this.categoryList = categoryList;
    return this;
  }

  public GetInventoryItemsSuccessInventoryList addCategoryListItem(GetInventoryItemsSuccessCategoryList categoryListItem) {
    if (this.categoryList == null) {
      this.categoryList = new ArrayList<GetInventoryItemsSuccessCategoryList>();
    }
    this.categoryList.add(categoryListItem);
    return this;
  }

  /**
   * List of item types present in inventory
   * @return categoryList
   **/
  @Schema(description = "List of item types present in inventory")
      @Valid
    public List<GetInventoryItemsSuccessCategoryList> getCategoryList() {
    return categoryList;
  }

  public void setCategoryList(List<GetInventoryItemsSuccessCategoryList> categoryList) {
    this.categoryList = categoryList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetInventoryItemsSuccessInventoryList getInventoryItemsSuccessInventoryList = (GetInventoryItemsSuccessInventoryList) o;
    return Objects.equals(this.categoryName, getInventoryItemsSuccessInventoryList.categoryName) &&
        Objects.equals(this.categoryList, getInventoryItemsSuccessInventoryList.categoryList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categoryName, categoryList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetInventoryItemsSuccessInventoryList {\n");
    
    sb.append("    categoryName: ").append(toIndentedString(categoryName)).append("\n");
    sb.append("    categoryList: ").append(toIndentedString(categoryList)).append("\n");
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
