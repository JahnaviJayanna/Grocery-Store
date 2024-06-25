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
 * Create user request body
 */
@Schema(description = "Create user request body", requiredProperties = {"firstName", "emailId", "msisdn","userName","userRole"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CreateUserPayload   {
  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("emailId")
  private String emailId = null;

  @JsonProperty("userName")
  private String userName = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("msisdn")
  private String msisdn = null;

  @JsonProperty("dob")
  private String dob = null;

  /**
   * WOrspace to which user wants to login
   */
  public enum UserRoleEnum {
    ADMIN("ADMIN"),

    STAFF("STAFF");

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

  public CreateUserPayload firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * First name of user
   * @return firstName
   **/
  @Schema(example = "John", description = "First name of user")
  @NotNull(message = "FirstName must not be null")
  @NotEmpty(message = "FirstName must not be empty")
  @Pattern(regexp="^[a-zA-Z]{0,50}$", message = "Invalid value for firstname")   public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public CreateUserPayload lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Last name of user
   * @return lastName
   **/
  @Schema(example = "James", description = "Last name of user")
  @Pattern(regexp="^[a-zA-Z]{0,50}$", message = "Invalid value for last name")   public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public CreateUserPayload emailId(String emailId) {
    this.emailId = emailId;
    return this;
  }

  /**
   * Email id of user
   * @return emailId
   **/
  @Schema(example = "john@email.com", description = "Email id of user")
  @NotNull(message = "emailId is required")
  @NotEmpty(message = "emailId must not be empty")
  @Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid value for emailId")   public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public CreateUserPayload userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * User Name for user
   * @return userName
   **/
  @Schema(example = "ADM753", description = "User Name for user")
  @NotNull(message = "userName is required")
  @NotEmpty(message = "UserName must not be empty")
  @Pattern(regexp="^[A-Z]{3}\\d{3}$", message = "Invalid value for UserName") @Size(min=6,max=6)   public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public CreateUserPayload password(String password) {
    this.password = password;
    return this;
  }

  /**
   * User account password
   * @return password
   **/
  @Schema(example = "12345", description = "User account password")
  @NotNull(message = "password is required")
  @NotEmpty(message = "Password must not be empty")
  @Pattern(regexp="^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password does not meet requirement, should contain atleast one alphabet, number and special character of minimum length 8")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public CreateUserPayload msisdn(String msisdn) {
    this.msisdn = msisdn;
    return this;
  }

  /**
   * Mobile number of user
   * @return msisdn
   **/
  @Schema(example = "1234567899", description = "Mobile number of user")
  @Pattern(regexp="^[0-9]+$", message = "Value for msisdn should be numeric")
  @NotEmpty(message = "msisdn must not be empty")
  @NotNull(message = "msisdn is required")
  @Size(min=10,max=13)   public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public CreateUserPayload dob(String dob) {
    this.dob = dob;
    return this;
  }

  /**
   * User dob
   * @return dob
   **/
  @Schema(example = "12/05/2002", description = "User dob")
  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public CreateUserPayload userRole(UserRoleEnum userRole) {
    this.userRole = userRole;
    return this;
  }

  /**
   * Role of user who wants to login
   * @return userRole
   **/
  @Schema(example = "ADMIN", description = "Role of user who wants to login")
  @NotNull(message = "userRole is required")
  @NotEmpty(message = "userRole must not be empty")
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
    CreateUserPayload createUserPayload = (CreateUserPayload) o;
    return Objects.equals(this.firstName, createUserPayload.firstName) &&
            Objects.equals(this.lastName, createUserPayload.lastName) &&
            Objects.equals(this.emailId, createUserPayload.emailId) &&
            Objects.equals(this.userName, createUserPayload.userName) &&
            Objects.equals(this.password, createUserPayload.password) &&
            Objects.equals(this.msisdn, createUserPayload.msisdn) &&
            Objects.equals(this.dob, createUserPayload.dob) &&
            Objects.equals(this.userRole, createUserPayload.userRole);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, emailId, userName, password, msisdn, dob, userRole);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateUserPayload {\n");

    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    emailId: ").append(toIndentedString(emailId)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    msisdn: ").append(toIndentedString(msisdn)).append("\n");
    sb.append("    dob: ").append(toIndentedString(dob)).append("\n");
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