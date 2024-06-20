package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;


/**
 * Request body for user login
 */
@Schema(description = "Request body for user login", requiredProperties = {"userRole", "password", "userName"})
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserLogin   {
  @JsonProperty("userName")
  private String userName = null;

  @JsonProperty("password")
  private String password = null;

  /**
   * Role of user who wants to login
   */

  public enum UserRoleEnum {
    ADMIN("ADMIN"),
    STAFF("STAFF");

    public String getValue() {
      return value;
    }

    private String value;

    UserRoleEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static UserRoleEnum fromValue(String text) {
      for (UserRoleEnum b : UserRoleEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("userRole")
  private UserRoleEnum userRole = null;

  public UserLogin userName(String userName) {
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
  @Pattern(regexp="^[A-Z]{3}\\d{3}$", message = "Enter valid user name of format XYZ123")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public UserLogin password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Password of user
   * @return password
   **/
  @Schema(example = "password", description = "Password of user")
  @NotNull(message = "password is required")
  @NotBlank(message = "Password is required")
  @NotEmpty(message = "Password is required")
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password should contain atleast one special character and alphabet")
  @Size(min=6, max =15, message = "Password size should be between 6 and 15")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserLogin userRole(UserRoleEnum userRole) {
    this.userRole = userRole;
    return this;
  }

  /**
   * ROle of user
   *
   * @return userRole
   **/
  @Schema(example = "ADMIN", description = "Role of user who wants to login")
  public String getUserRole() {
    return String.valueOf(userRole);
  }

  public void setUserRole(String userRole) {
    this.userRole = UserRoleEnum.valueOf(userRole);
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserLogin userLogin = (UserLogin) o;
    return Objects.equals(this.userName, userLogin.userName) &&
            Objects.equals(this.password, userLogin.password) &&
            Objects.equals(this.userRole, userLogin.userRole);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, password, userRole);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserLogin {\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    userRole: ").append(toIndentedString(userRole)).append("\n");
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