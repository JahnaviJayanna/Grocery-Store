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
 * Success response for logout
 */
@Schema(description = "Success response for logout", requiredProperties = {"userName", "message", "status"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class LogoutSucess   {
  @JsonProperty("userName")
  private String userName = null;

  @JsonProperty("transactionTimeStamp")
  private String transactionTimeStamp = null;

  @JsonProperty("message")
  private String message = null;

  /**
   * Status of request
   */
  public enum StatusEnum {
    SUCCEEDED("SUCCEEDED"),

    FAILED("FAILED");

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

  public LogoutSucess userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * User id of user
   * @return userName
   **/
  @Schema(example = "ADM753", description = "User id of user")
  @NotNull

  @Pattern(regexp="^[A-Z]{3}\\d{3}$") @Size(min=6,max=6)   public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public LogoutSucess transactionTimeStamp(String transactionTimeStamp) {
    this.transactionTimeStamp = transactionTimeStamp;
    return this;
  }

  /**
   * The timestamp when the event occurred
   * @return transactionTimeStamp
   **/
  @Schema(example = "2024-05-14T18:48:21", description = "The timestamp when the event occurred")

  public String getTransactionTimeStamp() {
    return transactionTimeStamp;
  }

  public void setTransactionTimeStamp(String transactionTimeStamp) {
    this.transactionTimeStamp = transactionTimeStamp;
  }

  public LogoutSucess message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Message on action performed
   * @return message
   **/
  @Schema(example = "User login successfull", description = "Message on action performed")
  @NotNull

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LogoutSucess status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Status of request
   * @return status
   **/
  @Schema(example = "SUCCEEDED", description = "Status of request")
  @NotNull

  public StatusEnum getStatus() {
    return status;
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
    LogoutSucess logoutSucess = (LogoutSucess) o;
    return Objects.equals(this.userName, logoutSucess.userName) &&
            Objects.equals(this.transactionTimeStamp, logoutSucess.transactionTimeStamp) &&
            Objects.equals(this.message, logoutSucess.message) &&
            Objects.equals(this.status, logoutSucess.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, transactionTimeStamp, message, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogoutSucess {\n");

    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    transactionTimeStamp: ").append(toIndentedString(transactionTimeStamp)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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