package io.swagger.api;

import io.swagger.exception.ApiException;
import io.swagger.exception.UnauthorizedException;
import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.util.JwtUtil;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-20T17:14:43.621571553Z[GMT]")
@RestController
public class V1ApiController implements V1Api {

    private static final Logger log = LoggerFactory.getLogger(V1ApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public V1ApiController(ObjectMapper objectMapper, HttpServletRequest request, JwtUtil jwtUtil, UserService userService, InventoryService inventoryService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.inventoryService = inventoryService;
    }


    public ResponseEntity<AddUpdateInventorySuccess> addItemsToInventory(@Parameter(in = ParameterIn.DEFAULT, description = "Add the details of items to inventory", required=true, schema=@Schema()) @RequestBody InventoryItemDetailsPayload body,
                                                                         HttpServletRequest request) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        String bearerToken = authorization.substring("Bearer".length()).trim();
        if (accept != null && accept.contains("application/json")) {
            if (authorization!= null && authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                if(jwtUtil.extractUserRole(bearerToken).equals("ADMIN")){
                    AddUpdateInventorySuccess response = inventoryService.createItemsToInventory(body, jwtUtil.extractUsername(bearerToken));
                    return new ResponseEntity<AddUpdateInventorySuccess>(response, HttpStatus.OK);
                }else {
                    throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User doest not previlage to perform action");
                }
            } else {
                throw new ApiException(HttpStatus.UNAUTHORIZED.value(), "User token is not valid");
            }
        }
        return new ResponseEntity<AddUpdateInventorySuccess>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<GetInventoryItemsSuccess> allInventoryListItems(@Parameter(in = ParameterIn.QUERY, description = "Id of the inventory item (optional, if provided filters the inventory based on itemId)" ,schema=@Schema()) @RequestParam(value = "itemId", required = false) @Valid @Pattern(regexp = "^[A-Z]{3}\\d{3}$") String itemId
            , @Parameter(in = ParameterIn.QUERY, description = "Name of the category of items (optional, if provided filters the inventory based on categoryName)" ,schema=@Schema()) @Valid @RequestParam(value = "categoryName", required = false) String categoryName
            , @Parameter(in = ParameterIn.QUERY, description = "Type of item (optional, if provided filters the inventory based on typeName)" ,schema=@Schema()) @Valid @RequestParam(value = "itemType", required = false) String itemType
            , @Parameter(in = ParameterIn.QUERY, description = "Item name (optional, if provided filters the inventory based on item name)" ,schema=@Schema()) @Valid @RequestParam(value = "itemName", required = false) String itemName
            , HttpServletRequest request) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        String bearerToken = authorization.substring("Bearer".length()).trim();
        if (accept != null && accept.contains("application/json")) {
            if (authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                GetInventoryItemsSuccess response = inventoryService.fetchInventoryList(itemId,itemName,itemType,categoryName, jwtUtil.extractUsername(bearerToken));
                return new ResponseEntity<GetInventoryItemsSuccess>(response, HttpStatus.OK);
            } else {
                throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is not valid");
            }
        }
        return new ResponseEntity<GetInventoryItemsSuccess>(HttpStatus.NOT_IMPLEMENTED);
    }


    public ResponseEntity<CreateUserSuccess> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "Created user object", schema=@Schema()) @Valid @RequestBody CreateUserPayload body, HttpServletRequest request
) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        if (accept != null && accept.contains("application/json")) {
            String bearerToken = authorization.substring("Bearer".length()).trim();
            System.out.println(jwtUtil.validateToken(bearerToken));
            if (authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                CreateUserSuccess response = userService.createUser(body, bearerToken);
                return new ResponseEntity<CreateUserSuccess>(response, HttpStatus.OK);
            } else {
                throw new ApiException(HttpStatus.UNAUTHORIZED.value(), "User token is not valid");
            }
        }
        return new ResponseEntity<CreateUserSuccess>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DeleteInventorySuccess> deleteInventory(@Parameter(in = ParameterIn.QUERY, description = "Id of the inventory item" ,schema=@Schema()) @NotEmpty @NotNull @Valid @RequestParam(value = "itemId", required = true) String itemId
            , HttpServletRequest request) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        if (accept != null && accept.contains("application/json")) {
            String bearerToken = authorization.substring("Bearer".length()).trim();
            if (authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                if(jwtUtil.extractUserRole(bearerToken).equals("ADMIN")){
                    DeleteInventorySuccess response = inventoryService.deleteItem(itemId);
                    return new ResponseEntity<DeleteInventorySuccess>(response, HttpStatus.OK);
                }else {
                    throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User doest not previlage to perform action");
                }
            } else {
                throw new ApiException(HttpStatus.UNAUTHORIZED.value(), "User token is not valid");
            }
        }
        return new ResponseEntity<DeleteInventorySuccess>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DeleteUserSuccess> deleteUser(@Parameter(in = ParameterIn.QUERY, description = "Id of user to deleted" ,required=true,schema=@Schema()) @NotNull @NotEmpty @Pattern(regexp = "^[A-Z]{2}\\.\\d{13}$") @Valid String userId,
                                                        HttpServletRequest request) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        if (accept != null && accept.contains("application/json")) {
            String bearerToken = authorization.substring("Bearer".length()).trim();
            if (authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                if(jwtUtil.extractUserRole(bearerToken).equals("ADMIN")){
                    DeleteUserSuccess response = userService.deleteUser(userId);
                    return new ResponseEntity<DeleteUserSuccess>(response, HttpStatus.OK);
                }else {
                    throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User doest not previlage to perform action");
                }
            } else {
                throw new ApiException(HttpStatus.UNAUTHORIZED.value(), "User token is not valid");
            }
        }
        return new ResponseEntity<DeleteUserSuccess>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<SellItemsSucess> fecthSalesBasedOntxnId(@Parameter(in = ParameterIn.QUERY, description = "txnId of which details needs to be fetched" ,required=true,schema=@Schema()) @NotNull @NotEmpty @Valid String txnId,
                                                                  HttpServletRequest request) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        String bearerToken = authorization.substring("Bearer".length()).trim();
        if (accept != null && accept.contains("application/json")) {
            if (authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                if(jwtUtil.extractUserRole(bearerToken).equals("ADMIN")){
                        SellItemsSucess response = inventoryService.fetchSalesDetails(txnId);
                        return new ResponseEntity<SellItemsSucess>(response, HttpStatus.OK);
                }else {
                    throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User doest not previlage to perform action");
                }
            } else {
                throw new ApiException(HttpStatus.UNAUTHORIZED.value(), "User token is not valid");
            }
        }

        return new ResponseEntity<SellItemsSucess>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<FetchUserDetailsSucess> fetchUserList( @Parameter(in = ParameterIn.QUERY, description = "Id of staff user to fetch if provided" ,schema=@Schema()) @RequestParam(value = "userId", required = false) @Pattern(regexp = "^[A-Z]{2}\\.\\d{13}$") @Valid String userId
            , HttpServletRequest request) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        String bearerToken = authorization.substring("Bearer".length()).trim();
        if (accept != null && accept.contains("application/json")) {
            if (authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                FetchUserDetailsSucess response = userService.fetchUserList(userId, bearerToken);
                return new ResponseEntity<FetchUserDetailsSucess>(response, HttpStatus.OK);
            } else {
                throw new ApiException(HttpStatus.UNAUTHORIZED.value(), "User token is not valid");
            }
        }
        return new ResponseEntity<FetchUserDetailsSucess>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LoginSucess> loginUser(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @RequestBody UserLogin body,
                                                 HttpServletRequest request) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if (accept != null && accept.contains("application/json")) {
            if(userService.loginAuth(authorization)){
                LoginSucess res = userService.userLogin(body);
                return new ResponseEntity<LoginSucess>(res, HttpStatus.OK);
            }else{
                throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User is not authorised");
            }
        }
        return new ResponseEntity<LoginSucess>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LogoutSucess> logoutUser(@Parameter(in = ParameterIn.DEFAULT, description = "Logs out user", schema=@Schema()) @RequestBody @Valid UserLogout body,
                                                   HttpServletRequest request) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        String bearerToken = authorization.substring("Bearer".length()).trim();
        if (accept != null && accept.contains("application/json")) {
            if (authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                LogoutSucess res = userService.userLogout(body, bearerToken);
                return new ResponseEntity<LogoutSucess>(res, HttpStatus.OK);
            }else{
                throw new ApiException(HttpStatus.UNAUTHORIZED.value(),"User token is not valid");
            }
        }
        return new ResponseEntity<LogoutSucess>(HttpStatus.NOT_IMPLEMENTED);
    }



    public ResponseEntity<SellItemsSucess> sellItems(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @RequestBody @Valid SellItemsPayload body, HttpServletRequest request) throws ApiException {
        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        if (accept != null && accept.contains("application/json")) {
            String bearerToken = authorization.substring("Bearer".length()).trim();
            if (authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                SellItemsSucess response = inventoryService.sellItems(body, jwtUtil.extractUsername(bearerToken));
                return new ResponseEntity<SellItemsSucess>(response, HttpStatus.OK);
            } else {
                throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is not valid");
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    public ResponseEntity<AddUpdateInventorySuccess> updateInventory(@Parameter(in = ParameterIn.PATH, description = "Id of inventory item", required=true, schema=@Schema()) @PathVariable("itemId") @Valid @Pattern(regexp = "^[A-Z]{3}\\d{3}$") @NotEmpty @NotNull String itemId
            , @Parameter(in = ParameterIn.DEFAULT, description = "Update inventory items", required=true, schema=@Schema()) @RequestBody @Valid UpdateInventoryItemsPayload body,
                                                                     HttpServletRequest request) throws ApiException {

        String accept = request.getHeader("Accept");
        final String authorization = request.getHeader("Authorization");
        if(authorization == null){
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is required");
        }
        String bearerToken = authorization.substring("Bearer".length()).trim();
        if (accept != null && accept.contains("application/json")) {
            if (authorization.startsWith("Bearer") && jwtUtil.validateToken(bearerToken)) {
                if(jwtUtil.extractUserRole(bearerToken).equals("ADMIN")){
                        AddUpdateInventorySuccess response = inventoryService.updateItemsInInventory(itemId, body, jwtUtil.extractUsername(bearerToken));
                        return new ResponseEntity<AddUpdateInventorySuccess>(response, HttpStatus.OK);
                }else {
                    throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User doest not previlage to perform action");
                }
            } else {
                throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.value(), "User token is not valid");
            }
        }

        return new ResponseEntity<AddUpdateInventorySuccess>(HttpStatus.NOT_IMPLEMENTED);
    }


}
