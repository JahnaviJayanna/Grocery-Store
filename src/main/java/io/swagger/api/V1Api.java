/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.56).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.exception.ApiException;
import io.swagger.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.*;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@Validated
public interface V1Api {

    @Operation(summary = "Add items to inventory", description = "API to add new item to inventory with all required details", security = {
        @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Admin" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfull addtion of new items to inventory", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddUpdateInventorySuccess.class))),
        
        @ApiResponse(responseCode = "400", description = "Error while adding items to inventory", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorised Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorisedError.class))),
        
        @ApiResponse(responseCode = "403", description = "Forbidden error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        
        @ApiResponse(responseCode = "406", description = "Acceptance error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcceptanceError.class))),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class))) })
    @RequestMapping(value = "/v1/create/inventory",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<AddUpdateInventorySuccess> addItemsToInventory(@Parameter(in = ParameterIn.DEFAULT, description = "Add the details of items to inventory", required=true, schema=@Schema()) @Valid @RequestBody InventoryItemDetailsPayload body
,HttpServletRequest request) throws ApiException;


    @Operation(summary = "List of items present in inventory", description = "This will fetch list of all items added to inventory.", security = {
        @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Admin", "Staff" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfull retrival of list of all items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetInventoryItemsSuccess.class))),
        
        @ApiResponse(responseCode = "400", description = "Error while fetching list of all items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorised Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorisedError.class))),
        
        @ApiResponse(responseCode = "403", description = "Forbidden error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        
        @ApiResponse(responseCode = "406", description = "Acceptance error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcceptanceError.class))),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class))) })
    @RequestMapping(value = "/v1/fetch/inventory",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<GetInventoryItemsSuccess> allInventoryListItems(@Parameter(in = ParameterIn.QUERY, description = "Id of the inventory item (optional, if provided filters the inventory based on itemId)" ,schema=@Schema()) @Valid @RequestParam(value = "itemId", required = false) @Pattern(regexp = "^[A-Z]{3}\\d{3}$")String itemId
, @Parameter(in = ParameterIn.QUERY, description = "Name of the category of items (optional, if provided filters the inventory based on categoryName)" ,schema=@Schema()) @Valid @RequestParam(value = "categoryName", required = false) String categoryName
, @Parameter(in = ParameterIn.QUERY, description = "Type of item (optional, if provided filters the inventory based on typeName)" ,schema=@Schema()) @Valid @RequestParam(value = "itemType", required = false) String itemType
, @Parameter(in = ParameterIn.QUERY, description = "Item name (optional, if provided filters the inventory based on item name)" ,schema=@Schema()) @Valid @RequestParam(value = "itemName", required = false) String itemName
, HttpServletRequest request) throws ApiException;


    @Operation(summary = "Create user", description = "User details are added.", security = {
            @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Admin" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfully created user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserSuccess.class))),
        
        @ApiResponse(responseCode = "400", description = "Error while logging in", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @RequestMapping(value = "/v1/create/user",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<CreateUserSuccess> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "Created user object", schema=@Schema()) @Valid @RequestBody CreateUserPayload body, HttpServletRequest request
) throws ApiException;


    @Operation(summary = "Delete inventory item", description = "Delete inventory based on itemId", security = {
        @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Admin" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfull deletion of item", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteInventorySuccess.class))),
        
        @ApiResponse(responseCode = "400", description = "Error while fetching list of all items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorised Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorisedError.class))),
        
        @ApiResponse(responseCode = "403", description = "Forbidden error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        
        @ApiResponse(responseCode = "406", description = "Acceptance error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcceptanceError.class))),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class))) })
    @RequestMapping(value = "/v1/delete/inventory",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<DeleteInventorySuccess> deleteInventory(@Parameter(in = ParameterIn.QUERY, description = "Id of the inventory item" ,schema=@Schema()) @NotEmpty @NotNull @Valid @RequestParam(value = "itemId", required = true) String itemId
, HttpServletRequest request) throws ApiException;


    @Operation(summary = "Delete  user", description = "Delete  user based on userId", security = {
        @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Admin" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfull deletion of user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteInventorySuccess.class))),
        
        @ApiResponse(responseCode = "400", description = "Error while deleting user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @RequestMapping(value = "/v1/delete/user",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<DeleteUserSuccess> deleteUser(@NotNull @NotEmpty @Pattern(regexp="^[A-Z]{2}\\.\\d{13}$") @Parameter(in = ParameterIn.QUERY, description = "Id of user to deleted" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "userId", required = true) String userId
, HttpServletRequest request) throws ApiException;

    @Operation(summary = "List of items sold based on txnId", description = "This will fetch list of all item details Sold based on txnId.", security = {
            @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Sales" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfull retrival of list of all items sold", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SellItemsSucess.class))),

            @ApiResponse(responseCode = "400", description = "Error while fetching list of all items sold", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

            @ApiResponse(responseCode = "401", description = "Unauthorised Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorisedError.class))),

            @ApiResponse(responseCode = "403", description = "Forbidden error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),

            @ApiResponse(responseCode = "406", description = "Acceptance error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcceptanceError.class))),

            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class))) })
    @RequestMapping(value = "/v1/sell/inventory",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<SellItemsSucess> fecthSalesBasedOntxnId(@NotNull @NotEmpty @Parameter(in = ParameterIn.QUERY, description = "txnId of which details needs to be fetched" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "txnId", required = true) String txnId
    , HttpServletRequest request) throws ApiException;

    @Operation(summary = "Fetch users", description = "Fetch all user details list if query param is not passed", security = {
        @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Admin", "Staff" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfull retrival of staff user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FetchUserDetailsSucess.class))),
        
        @ApiResponse(responseCode = "400", description = "Error while fetching user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @RequestMapping(value = "/v1/fetch/user",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<FetchUserDetailsSucess> fetchUserList( @Pattern(regexp="^[A-Z]{2}\\.\\d{13}$") @Parameter(in = ParameterIn.QUERY, description = "Id of staff user to fetch if provided" ,schema=@Schema()) @Valid @RequestParam(value = "userId", required = false) String userId
, HttpServletRequest request) throws ApiException;


    @Operation(summary = "", description = "Logs user into the system", security = {
            @SecurityRequirement(name = "basicAuth")    },tags={ "Admin", "Staff" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfull user login", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginSucess.class))),
        
        @ApiResponse(responseCode = "400", description = "Error while logging in", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @RequestMapping(value = "/v1/user/login",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<LoginSucess> loginUser(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody UserLogin body
, HttpServletRequest request) throws ApiException;


    @Operation(summary = "", description = "Logs out current logged in user session", security = {
        @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Admin", "Staff" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfull user logout", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LogoutSucess.class))),
        
        @ApiResponse(responseCode = "400", description = "Error while fetching list of all items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @RequestMapping(value = "/v1/user/logout",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<LogoutSucess> logoutUser(@Parameter(in = ParameterIn.DEFAULT, description = "Logs out user", schema=@Schema()) @Valid @RequestBody UserLogout body
, HttpServletRequest request) throws ApiException;


    @Operation(summary = "Sell inventory items", description = "API for staff to sell items present in inventory", security = {
        @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Sales" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfull sale of item", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SellItemsSucess.class)))),
        
        @ApiResponse(responseCode = "400", description = "Error while selling items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorised Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorisedError.class))),
        
        @ApiResponse(responseCode = "403", description = "Forbidden error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        
        @ApiResponse(responseCode = "406", description = "Acceptance error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcceptanceError.class))),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class))) })
    @RequestMapping(value = "/v1/sell/inventory",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<SellItemsSucess> sellItems(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody SellItemsPayload body
, HttpServletRequest request) throws ApiException;


    @Operation(summary = "Update inventory item details", description = "Updates quantity, price and status of inventory details", security = {
        @SecurityRequirement(name = "bearerAuthentication")    }, tags={ "Admin" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfull Updation of inventory items", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AddUpdateInventorySuccess.class)))),
        
        @ApiResponse(responseCode = "400", description = "Error while fetching list of all items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorised Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorisedError.class))),
        
        @ApiResponse(responseCode = "403", description = "Forbidden error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        
        @ApiResponse(responseCode = "406", description = "Acceptance error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcceptanceError.class))),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class))) })
    @RequestMapping(value = "/v1/update/inventory/{itemId}",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<AddUpdateInventorySuccess> updateInventory(@Parameter(in = ParameterIn.PATH, description = "Id of inventory item", required=true, schema=@Schema()) @Valid @PathVariable("itemId") @Pattern(regexp = "^[A-Z]{3}\\d{3}$") @NotEmpty @NotNull String itemId
, @Parameter(in = ParameterIn.DEFAULT, description = "Update inventory items", required=true, schema=@Schema()) @Valid @RequestBody UpdateInventoryItemsPayload body
, HttpServletRequest request) throws ApiException;

}

