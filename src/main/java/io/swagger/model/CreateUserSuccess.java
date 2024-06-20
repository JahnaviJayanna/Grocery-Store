package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.ResponseParams;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Response succesfull user creation
 */
@Schema(description = "Response succesfull user creation", requiredProperties = {"userName", "userId"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CreateUserSuccess extends ResponseParams  {
  @JsonProperty("userName")
  private String userName = null;

  @JsonProperty("userId")
  private String userId = null;

  public CreateUserSuccess userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * User Name of user
   * @return userName
   **/
  @Schema(example = "ADM753", description = "User Name of user")
  @NotNull

  @Pattern(regexp="^[A-Z]{3}\\d{3}$") @Size(min=6,max=6)   public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public CreateUserSuccess userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * System Generated userId
   * @return userId
   **/
  @Schema(example = "US.1234", description = "System Generated userId")
  @NotNull

  @Pattern(regexp="^[A-Z]{2}\\.\\d{13}$")   public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateUserSuccess createUserSuccess = (CreateUserSuccess) o;
    return Objects.equals(this.userName, createUserSuccess.userName) &&
            Objects.equals(this.userId, createUserSuccess.userId) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, userId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateUserSuccess {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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