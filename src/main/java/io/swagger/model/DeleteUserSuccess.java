package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * DeleteInventorySuccess
 */
@Schema(description = "Response for delete success", requiredProperties = {"userId"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DeleteUserSuccess extends ResponseParams  {
  @JsonProperty("userId")
  private String userId = null;

  public DeleteUserSuccess itemId(String itemId) {
    this.userId = itemId;
    return this;
  }

  /**
   * Item id for deleted item
   * @return userId
   **/
  @Schema(example = "GS2736",description = "Item id for deleted item")
  @NotNull

  @Pattern(regexp="^[A-Z]{2}\\d{4}$") @Size(min=3,max=10)   public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteUserSuccess deleteInventorySuccess = (DeleteUserSuccess) o;
    return Objects.equals(this.userId, deleteInventorySuccess.userId) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteInventorySuccess {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}