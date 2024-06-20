package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.ResponseParams;
import io.swagger.model.SellItemsSucessSalesDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.*;

/**
 * Response for successfull sales for item
 */
@Schema(description = "Response for successfull sales for item", requiredProperties = {"transactionId","salesDetails"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class SellItemsSucess extends ResponseParams  {
  @JsonProperty("transactionId")
  private String transactionId = null;

  @JsonProperty("salesDetails")
  private SellItemsSucessSalesDetails salesDetails = null;

  public SellItemsSucess transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * The unique identifier for the transaction
   * @return transactionId
   **/
  @Schema(example = "PP201012.1345", description = "The unique identifier for the transaction")
  @NotNull

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public SellItemsSucess salesDetails(SellItemsSucessSalesDetails salesDetails) {
    this.salesDetails = salesDetails;
    return this;
  }

  /**
   * Get salesDetails
   * @return salesDetails
   **/
  @Schema(description = "Details of sales")
  @NotNull

  @Valid
  public SellItemsSucessSalesDetails getSalesDetails() {
    return salesDetails;
  }

  public void setSalesDetails(SellItemsSucessSalesDetails salesDetails) {
    this.salesDetails = salesDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SellItemsSucess sellItemsSucess = (SellItemsSucess) o;
    return Objects.equals(this.transactionId, sellItemsSucess.transactionId) &&
            Objects.equals(this.salesDetails, sellItemsSucess.salesDetails) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, salesDetails, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SellItemsSucess {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    salesDetails: ").append(toIndentedString(salesDetails)).append("\n");
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