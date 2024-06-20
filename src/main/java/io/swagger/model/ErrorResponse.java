package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.ResponseErrors;
import io.swagger.model.ResponseParams;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.*;

/**
 * Representing a Error Response for all transactions.
 */
@Schema(description = "Representing a Error Response for all transactions.", requiredProperties = {"errorUserMsg", "httpErrorCode", "errors"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ErrorResponse extends ResponseParams  {
  @JsonProperty("errorUserMsg")
  private String errorUserMsg = null;

  @JsonProperty("httpErrorCode")
  private String httpErrorCode = null;

  @JsonProperty("errors")
  @Valid
  private List<ResponseErrors> errors = new ArrayList<ResponseErrors>();

  @JsonProperty("transactionId")
  private String transactionId = null;

  public ErrorResponse errorUserMsg(String errorUserMsg) {
    this.errorUserMsg = errorUserMsg;
    return this;
  }

  /**
   * Generic error message
   * @return errorUserMsg
   **/
  @Schema(example = "Bad request", description = "Generic error message")
      @NotNull

    public String getErrorUserMsg() {
    return errorUserMsg;
  }

  public void setErrorUserMsg(String errorUserMsg) {
    this.errorUserMsg = errorUserMsg;
  }

  public ErrorResponse httpErrorCode(String httpErrorCode) {
    this.httpErrorCode = httpErrorCode;
    return this;
  }

  /**
   * HTTP error code
   * @return httpErrorCode
   **/
  @Schema(example = "400", description = "HTTP error code")
      @NotNull

    public String getHttpErrorCode() {
    return httpErrorCode;
  }

  public void setHttpErrorCode(String httpErrorCode) {
    this.httpErrorCode = httpErrorCode;
  }

  public ErrorResponse errors(List<ResponseErrors> errors) {
    this.errors = errors;
    return this;
  }

  public ErrorResponse addErrorsItem(ResponseErrors errorsItem) {
    this.errors.add(errorsItem);
    return this;
  }

  /**
   * Errors list.
   * @return errors
   **/
  @Schema(example = "[{\"code\":\"Error code\",\"message\":\"Error Message\"}]",description = "Errors list.")
      @NotNull
    @Valid
  @Size(max=5)   public List<ResponseErrors> getErrors() {
    return errors;
  }

  public void setErrors(List<ResponseErrors> errors) {
    this.errors = errors;
  }

  public ErrorResponse transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * The unique identifier for the transaction
   * @return transactionId
   **/
  @Schema(example = "PP201012.1345.A65040", description = "The unique identifier for the transaction")
      @NotNull

    public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.errorUserMsg, errorResponse.errorUserMsg) &&
        Objects.equals(this.httpErrorCode, errorResponse.httpErrorCode) &&
        Objects.equals(this.errors, errorResponse.errors) &&
        Objects.equals(this.transactionId, errorResponse.transactionId) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorUserMsg, httpErrorCode, errors, transactionId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    errorUserMsg: ").append(toIndentedString(errorUserMsg)).append("\n");
    sb.append("    httpErrorCode: ").append(toIndentedString(httpErrorCode)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
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
