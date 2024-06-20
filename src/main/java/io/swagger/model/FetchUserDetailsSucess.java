package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.ResponseParams;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.*;

/**
 * Response succesfull user creation
 */
@Schema(description = "Response succesfull user creation", requiredProperties = {"userDetails"})
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T05:52:39.355939416Z[GMT]")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class FetchUserDetailsSucess extends ResponseParams  {
  @JsonProperty("userDetails")
  @Valid
  private List<AllOfFetchUserDetailsSucessUserDetailsItems> userDetails = new ArrayList<AllOfFetchUserDetailsSucessUserDetailsItems>();

  public FetchUserDetailsSucess userDetails(List<AllOfFetchUserDetailsSucessUserDetailsItems> userDetails) {
    this.userDetails = userDetails;
    return this;
  }

  public FetchUserDetailsSucess addUserDetailsItem(AllOfFetchUserDetailsSucessUserDetailsItems userDetailsItem) {
    this.userDetails.add(userDetailsItem);
    return this;
  }

  /**
   * Get userDetails
   * @return userDetails
   **/
  @Schema(description = "")
  @NotNull

  public List<AllOfFetchUserDetailsSucessUserDetailsItems> getUserDetails() {
    return userDetails;
  }

  public void setUserDetails(List<AllOfFetchUserDetailsSucessUserDetailsItems> userDetails) {
    this.userDetails = userDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FetchUserDetailsSucess fetchUserDetailsSucess = (FetchUserDetailsSucess) o;
    return Objects.equals(this.userDetails, fetchUserDetailsSucess.userDetails) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userDetails, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FetchUserDetailsSucess {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    userDetails: ").append(toIndentedString(userDetails)).append("\n");
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