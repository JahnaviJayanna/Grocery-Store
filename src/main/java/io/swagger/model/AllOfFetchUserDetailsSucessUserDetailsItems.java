package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * AllOfFetchUserDetailsSucessUserDetailsItems
 */
@Schema(requiredProperties = {"status"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AllOfFetchUserDetailsSucessUserDetailsItems extends CreateUserPayload  {
  @JsonProperty("userId")
  private String userId = null;

  /**
   * Status of user
   */
  public enum StatusEnum {
    ACTIVE("ACTIVE"),
    
    INACTIVE("INACTIVE");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("status")
  private StatusEnum status = null;


  public AllOfFetchUserDetailsSucessUserDetailsItems userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * User userId
   * @return userId
   **/
  @Schema(example = "US.1716802561574", description = "User userId")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public AllOfFetchUserDetailsSucessUserDetailsItems status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Status of user
   * @return status
   **/
  @Schema(example = "Y", description = "Status of user")
  @NotNull

    public String getStatus() {
    return String.valueOf(status);
  }

  public void setStatus(String status) {
    this.status = StatusEnum.valueOf(status);
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllOfFetchUserDetailsSucessUserDetailsItems allOfFetchUserDetailsSucessUserDetailsItems = (AllOfFetchUserDetailsSucessUserDetailsItems) o;
    return Objects.equals(this.status, allOfFetchUserDetailsSucessUserDetailsItems.status) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllOfFetchUserDetailsSucessUserDetailsItems {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
