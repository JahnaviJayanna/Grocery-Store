package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Error response message
 */
@Schema(description = "Error response message", requiredProperties = {"message"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ResponseErrors   {
  @JsonProperty("code")
  private String code = null;

  @JsonProperty("message")
  private String message = null;

  public ResponseErrors code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Error Code
   * @return code
   **/
  @Schema(example = "invalid.value", description = "Error Code")
  
  @Size(max=20)   public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ResponseErrors message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Error message
   * @return message
   **/
  @Schema(example = "Values passed are invalid", description = "Error message")
      @NotNull

  @Size(max=100)   public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseErrors responseErrors = (ResponseErrors) o;
    return Objects.equals(this.code, responseErrors.code) &&
        Objects.equals(this.message, responseErrors.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseErrors {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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
