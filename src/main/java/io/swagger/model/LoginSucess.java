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
 * Success response for login
 */
@Schema(description = "Success response for login", requiredProperties = {"accessToken", "message", "status"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class LoginSucess   {
  @JsonProperty("accessToken")
  private String accessToken = null;

  @JsonProperty("transactionTimeStamp")
  private String transactionTimeStamp = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("userId")
  private String userId = null;

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

  public LoginSucess accessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  /**
   * Accesstoken generated for user
   * @return accessToken
   **/
  @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", description = "Accesstoken generated for user")
  @NotNull

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public LoginSucess transactionTimeStamp(String transactionTimeStamp) {
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

  public LoginSucess message(String message) {
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

  public LoginSucess userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Syatem generated userId
   * @return userId
   **/
  @Schema(example = "US.1234", description = "Syatem generated userId")

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public LoginSucess status(StatusEnum status) {
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
    LoginSucess loginSucess = (LoginSucess) o;
    return Objects.equals(this.accessToken, loginSucess.accessToken) &&
            Objects.equals(this.transactionTimeStamp, loginSucess.transactionTimeStamp) &&
            Objects.equals(this.message, loginSucess.message) &&
            Objects.equals(this.userId, loginSucess.userId) &&
            Objects.equals(this.status, loginSucess.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessToken, transactionTimeStamp, message, userId, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginSucess {\n");

    sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
    sb.append("    transactionTimeStamp: ").append(toIndentedString(transactionTimeStamp)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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