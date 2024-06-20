package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Request body for user logout
 */
@Schema(description = "Request body for user logout")
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserLogout   {
  @JsonProperty("userName")
  private String userName = null;

  public UserLogout userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * User Name of user
   * @return userName
   **/
  @Schema(example = "ADM753", description = "User Name of user")
  @NotNull(message = "User name is required")
  @NotEmpty(message = "User name is required")
  @NotBlank(message = "User name is required")
  @Pattern(regexp="^[A-Z]{3}\\d{3}$") @Size(min=6,max=6)   public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserLogout userLogout = (UserLogout) o;
    return Objects.equals(this.userName, userLogout.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserLogout {\n");
    
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
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
