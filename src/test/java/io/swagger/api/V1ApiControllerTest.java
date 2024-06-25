package io.swagger.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.swagger.configuration.LocalDateConverter;
import io.swagger.configuration.LocalDateTimeConverter;
import io.swagger.exception.ApiException;
import io.swagger.exception.GlobalExceptionHandler;
import io.swagger.exception.UnauthorizedException;
import io.swagger.model.*;
import io.swagger.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.SmartValidator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class V1ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private V1ApiController v1ApiController;

    @Mock
    private SmartValidator validator;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(v1ApiController)
                                .setControllerAdvice(new GlobalExceptionHandler())
                                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateUser_Success() throws Exception {
        CreateUserPayload payload = createUser();
        CreateUserSuccess successResponse = new CreateUserSuccess();

        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(userService.createUser(any(CreateUserPayload.class), eq("validToken"))).thenReturn(successResponse);

        mockMvc.perform(post("/v1/create/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer validToken")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser_Unauthorized() throws Exception {
        CreateUserPayload payload = createUser();

        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        mockMvc.perform(post("/v1/create/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalidToken")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApiException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    public void testCreateUser_MissingAuthorizationHeader() throws Exception {
        CreateUserPayload payload = createUser();

        mockMvc.perform(post("/v1/create/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    public void testCreateUser_NotImplemented() throws Exception {
        CreateUserPayload payload = createUser();

        mockMvc.perform(post("/v1/create/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalidToken")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void testFetchUserDetails_Success() throws Exception{

        when(jwtUtil.validateToken("validToken")).thenReturn(true);

        mockMvc.perform(get("/v1/fetch/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer validToken")
                        .content(objectMapper.writeValueAsString("payload")))
                .andExpect(status().isOk());

    }

    @Test
    public void testFetchUserDetails_Unauthorized() throws Exception {
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/fetch/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalidToken")
                        .content(objectMapper.writeValueAsString("payload")))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApiException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    public void testFetchUserDetails_MissingAuthorizationHeader() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/fetch/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("payload")))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    public void testFetchUserDetails_NotImplemented() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/fetch/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalidToken")
                        .content(objectMapper.writeValueAsString("payload")))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void testDeleteUser_Success() throws Exception{

        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(jwtUtil.extractUserRole("validToken")).thenReturn("ADMIN");

        mockMvc.perform(delete("/v1/delete/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer validToken")
                        .param("userId", "easdfg"))
                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteUser_Unprevilaged() throws Exception {
        when(jwtUtil.validateToken("invalidToken")).thenReturn(true);
        when(jwtUtil.extractUserRole("invalidToken")).thenReturn("STAFF");

        mockMvc.perform(delete("/v1/delete/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalidToken")
                        .param("userId", "wert"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User doest not previlage to perform action", result.getResolvedException().getMessage()));
    }

    @Test
    public void testDeleteUser_Unauthorized() throws Exception {
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        mockMvc.perform(delete("/v1/delete/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalidToken")
                        .param("userId", "wert"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApiException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    public void testDeleteUser_MissingAuthorizationHeader() throws Exception {

        mockMvc.perform(delete("/v1/delete/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "wert"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    public void testDeleteUser_NotImplemented() throws Exception {

        mockMvc.perform(delete("/v1/delete/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalidToken")
                        .param("userId", "wert"))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        UserLogin payload = new UserLogin();
        payload.setUserRole("ADMIN");
        payload.setUserName("AQW123");
        payload.setPassword("AShsd@133");
        LoginSucess successResponse = new LoginSucess();

        when(userService.loginAuth(anyString())).thenReturn(true);
        when(userService.userLogin(payload)).thenReturn(successResponse);

        mockMvc.perform(post("/v1/user/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer validToken")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginUser_Unauthorized() throws Exception {
        UserLogin payload = new UserLogin();
        payload.setUserRole("ADMIN");
        payload.setUserName("AQW123");
        payload.setPassword("AShsd@133");
        mockMvc.perform(post("/v1/user/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic invalidToken")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User is not authorised", result.getResolvedException().getMessage()));
    }

    @Test
    public void testLoginUser_NotImplemented() throws Exception {
        UserLogin payload = new UserLogin();
        payload.setUserRole("ADMIN");
        payload.setUserName("AQW123");
        payload.setPassword("AShsd@133");
        mockMvc.perform(post("/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic invalidToken")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void testLogoutUser_Success() throws Exception {
        UserLogout payload = new UserLogout();
        payload.setUserName("AQW123");
        LogoutSucess successResponse = new LogoutSucess();

        when(jwtUtil.validateToken(anyString())).thenReturn(true);
        when(userService.userLogout(payload,"validToken")).thenReturn(successResponse);

        mockMvc.perform(post("/v1/user/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer validToken")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogoutUser_Unauthorized() throws Exception {
        UserLogout payload = new UserLogout();
        payload.setUserName("ADM123");

        mockMvc.perform(post("/v1/user/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalidToken")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApiException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    public void testLogoutUser_MissingAuthorizationHeader() throws Exception {
        UserLogout payload = new UserLogout();
        payload.setUserName("ADM123");

        mockMvc.perform(post("/v1/user/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    public void testLogoutUser_NotImplemented() throws Exception {
        UserLogout payload = new UserLogout();
        payload.setUserName("ADM123");

        mockMvc.perform(post("/v1/user/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalidToken")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void testAddItemsToInventory_Success() throws Exception {

        InventoryItemDetailsPayload payload = createInventoryPayload();
        AddUpdateInventorySuccess successResponse = new AddUpdateInventorySuccess();

        when(jwtUtil.validateToken(anyString())).thenReturn(true);
        when(jwtUtil.extractUserRole(anyString())).thenReturn("ADMIN");
        when(jwtUtil.extractUsername(anyString())).thenReturn("USR1223");
        when(inventoryService.createItemsToInventory(payload,"USR1223")).thenReturn(successResponse);

        // Manually construct the JSON string
        String jsonString = "{"
                + "\"categoryName\":\"" + payload.getCategoryName().toString() + "\","
                + "\"itemType\":\"" + payload.getItemType().toString() + "\","
                + "\"itemDetails\":[{"
                + "\"itemName\":\"" + payload.getItemDetails().get(0).getItemName() + "\","
                + "\"description\":\"" + payload.getItemDetails().get(0).getDescription() + "\","
                + "\"price\":" + payload.getItemDetails().get(0).getPrice() + ","
                + "\"quantity\":" + payload.getItemDetails().get(0).getQuantity() + ","
                + "\"units\":" + payload.getItemDetails().get(0).getUnits()
                + "}]"
                + "}";

        mockMvc.perform(post("/v1/create/inventory")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer validToken")
                        .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddItemsToInventory_Unauthorized() throws Exception {
        InventoryItemDetailsPayload payload = createInventoryPayload();
        AddUpdateInventorySuccess successResponse = new AddUpdateInventorySuccess();

        when(jwtUtil.validateToken(anyString())).thenReturn(false);

        // Manually construct the JSON string
        String jsonString = "{"
                + "\"categoryName\":\"" + payload.getCategoryName().toString() + "\","
                + "\"itemType\":\"" + payload.getItemType().toString() + "\","
                + "\"itemDetails\":[{"
                + "\"itemName\":\"" + payload.getItemDetails().get(0).getItemName() + "\","
                + "\"description\":\"" + payload.getItemDetails().get(0).getDescription() + "\","
                + "\"price\":" + payload.getItemDetails().get(0).getPrice() + ","
                + "\"quantity\":" + payload.getItemDetails().get(0).getQuantity() + ","
                + "\"units\":" + payload.getItemDetails().get(0).getUnits()
                + "}]"
                + "}";

        mockMvc.perform(post("/v1/create/inventory")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer validToken")
                        .content(jsonString))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApiException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    public void testAddItemsToInventory_NotImplemented() throws Exception {
        InventoryItemDetailsPayload payload = createInventoryPayload();

        // Manually construct the JSON string
        String jsonString = "{"
                + "\"categoryName\":\"" + payload.getCategoryName().toString() + "\","
                + "\"itemType\":\"" + payload.getItemType().toString() + "\","
                + "\"itemDetails\":[{"
                + "\"itemName\":\"" + payload.getItemDetails().get(0).getItemName() + "\","
                + "\"description\":\"" + payload.getItemDetails().get(0).getDescription() + "\","
                + "\"price\":" + payload.getItemDetails().get(0).getPrice() + ","
                + "\"quantity\":" + payload.getItemDetails().get(0).getQuantity() + ","
                + "\"units\":" + payload.getItemDetails().get(0).getUnits()
                + "}]"
                + "}";

        mockMvc.perform(post("/v1/create/inventory")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer validToken")
                        .content(jsonString))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void testAddItemsToInventory_Unprevilaged() throws Exception {
        InventoryItemDetailsPayload payload = createInventoryPayload();

        when(jwtUtil.validateToken(anyString())).thenReturn(true);
        when(jwtUtil.extractUserRole(anyString())).thenReturn("STAFF");

        // Manually construct the JSON string
        String jsonString = "{"
                + "\"categoryName\":\"" + payload.getCategoryName().toString() + "\","
                + "\"itemType\":\"" + payload.getItemType().toString() + "\","
                + "\"itemDetails\":[{"
                + "\"itemName\":\"" + payload.getItemDetails().get(0).getItemName() + "\","
                + "\"description\":\"" + payload.getItemDetails().get(0).getDescription() + "\","
                + "\"price\":" + payload.getItemDetails().get(0).getPrice() + ","
                + "\"quantity\":" + payload.getItemDetails().get(0).getQuantity() + ","
                + "\"units\":" + payload.getItemDetails().get(0).getUnits()
                + "}]"
                + "}";

        mockMvc.perform(post("/v1/create/inventory")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer validToken")
                        .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User doest not previlage to perform action", result.getResolvedException().getMessage()));
    }

    @Test
    public void testAddItemsToInventory_MissingAuthorizationHeader() throws Exception {
        InventoryItemDetailsPayload payload = createInventoryPayload();

        // Manually construct the JSON string
        String jsonString = "{"
                + "\"categoryName\":\"" + payload.getCategoryName().toString() + "\","
                + "\"itemType\":\"" + payload.getItemType().toString() + "\","
                + "\"itemDetails\":[{"
                + "\"itemName\":\"" + payload.getItemDetails().get(0).getItemName() + "\","
                + "\"description\":\"" + payload.getItemDetails().get(0).getDescription() + "\","
                + "\"price\":" + payload.getItemDetails().get(0).getPrice() + ","
                + "\"quantity\":" + payload.getItemDetails().get(0).getQuantity() + ","
                + "\"units\":" + payload.getItemDetails().get(0).getUnits()
                + "}]"
                + "}";

        mockMvc.perform(post("/v1/create/inventory")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonString))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    void testAllInventoryListItems_Success() throws Exception {
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;
        GetInventoryItemsSuccess successResponse = new GetInventoryItemsSuccess();

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(jwtUtil.extractUsername(validToken)).thenReturn("username");
        when(inventoryService.fetchInventoryList(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(successResponse);

        mockMvc.perform(get("/v1/fetch/inventory")
                        .param("itemId", "itemIdValue")
                        .param("categoryName", "categoryNameValue")
                        .param("itemType", "itemTypeValue")
                        .param("itemName", "itemNameValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void testAllInventoryListItems_Unauthorized() throws Exception {
        String invalidToken = "invalidToken";
        String bearerToken = "Bearer " + invalidToken;

        when(jwtUtil.validateToken(invalidToken)).thenReturn(false);

        mockMvc.perform(get("/v1/fetch/inventory")
                        .param("itemId", "itemIdValue")
                        .param("categoryName", "categoryNameValue")
                        .param("itemType", "itemTypeValue")
                        .param("itemName", "itemNameValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    void testAllInventoryListItems_MissingAuthorizationHeader() throws Exception {

        mockMvc.perform(get("/v1/fetch/inventory")
                        .param("itemId", "itemIdValue")
                        .param("categoryName", "categoryNameValue")
                        .param("itemType", "itemTypeValue")
                        .param("itemName", "itemNameValue")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    void testAllInventoryListItems_NotImplemented() throws Exception {
        String bearerToken = "Bearer invalidToken";

        mockMvc.perform(get("/v1/fetch/inventory")
                        .param("itemId", "itemIdValue")
                        .param("categoryName", "categoryNameValue")
                        .param("itemType", "itemTypeValue")
                        .param("itemName", "itemNameValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNotImplemented());
    }

    @Test
    void testDeleteInventory_Success() throws Exception {
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;
        DeleteInventorySuccess successResponse = new DeleteInventorySuccess();

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(jwtUtil.extractUserRole(validToken)).thenReturn("ADMIN");
        when(inventoryService.deleteItem(anyString())).thenReturn(successResponse);

        mockMvc.perform(delete("/v1/delete/inventory")
                        .param("itemId", "itemIdValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteInventory_UnprevilagedUser() throws Exception {
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(jwtUtil.extractUserRole(validToken)).thenReturn("USER");

        mockMvc.perform(delete("/v1/delete/inventory")
                        .param("itemId", "itemIdValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User doest not previlage to perform action", result.getResolvedException().getMessage()));
    }

    @Test
    void testDeleteInventory_UnauthorizedUser() throws Exception {
        String invalidToken = "invalidToken";
        String bearerToken = "Bearer " + invalidToken;

        when(jwtUtil.validateToken(invalidToken)).thenReturn(false);

        mockMvc.perform(delete("/v1/delete/inventory")
                        .param("itemId", "itemIdValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApiException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    void testDeleteInventory_MissingAuthorizationHeader() throws Exception {

        mockMvc.perform(delete("/v1/delete/inventory")
                        .param("itemId", "itemIdValue")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    void testDeleteInventory_NotImplemented() throws Exception {
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;

        mockMvc.perform(delete("/v1/delete/inventory")
                        .param("itemId", "itemIdValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNotImplemented());
    }

    @Test
    void testFetchSalesBasedOnTxnId_Success() throws Exception {
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;
        SellItemsSucess mockResponse = new SellItemsSucess();

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(jwtUtil.extractUserRole(validToken)).thenReturn("ADMIN");
        when(inventoryService.fetchSalesDetails(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/v1/sell/inventory")
                        .param("txnId", "txnIdValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchSalesBasedOnTxnId_UnprevilagedUser() throws Exception {
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(jwtUtil.extractUserRole(validToken)).thenReturn("USER");

        mockMvc.perform(get("/v1/sell/inventory")
                        .param("txnId", "txnIdValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User doest not previlage to perform action", result.getResolvedException().getMessage()));
    }

    @Test
    void testFetchSalesBasedOnTxnId_UnauthorisedUser() throws Exception {
        String invalidToken = "invalidToken";
        String bearerToken = "Bearer " + invalidToken;

        when(jwtUtil.validateToken(invalidToken)).thenReturn(false);

        mockMvc.perform(get("/v1/sell/inventory")
                        .param("txnId", "txnIdValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApiException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    void testFetchSalesBasedOnTxnId_MissingAuthorizationHeader() throws Exception {

        mockMvc.perform(get("/v1/sell/inventory")
                        .param("txnId", "txnIdValue")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    void testFetchSalesBasedOnTxnId_NotImplemented() throws Exception {
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;

        mockMvc.perform(get("/v1/sell/inventory")
                        .param("txnId", "txnIdValue")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNotImplemented());
    }

    @Test
    void testSellItems_Success() throws Exception {
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;
        SellItemsPayload payload = sellItemsPayload();
        SellItemsSucess mockResponse = new SellItemsSucess();

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(jwtUtil.extractUsername(validToken)).thenReturn("admin");
        when(inventoryService.sellItems(any(SellItemsPayload.class), anyString())).thenReturn(mockResponse);

        mockMvc.perform(post("/v1/sell/inventory")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }

    @Test
    void testSellItems_UnauthorisedUser() throws Exception {
        String invalidToken = "invalidToken";
        String bearerToken = "Bearer " + invalidToken;
        SellItemsPayload payload = sellItemsPayload();

        when(jwtUtil.validateToken(invalidToken)).thenReturn(false);

        mockMvc.perform(post("/v1/sell/inventory")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    void testSellItems_NotImplemented() throws Exception {
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;
        SellItemsPayload payload = sellItemsPayload();

        mockMvc.perform(post("/v1/sell/inventory")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isNotImplemented());
    }

    @Test
    void testSellItems_MissingAuthorizationHeader() throws Exception {
        SellItemsPayload payload = sellItemsPayload();

        mockMvc.perform(post("/v1/sell/inventory")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    void testUpdateInventory_Success() throws Exception {
        String itemId = "ITEM001";
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;
        UpdateInventoryItemsPayload payload = new UpdateInventoryItemsPayload();
        AddUpdateInventorySuccess mockResponse = new AddUpdateInventorySuccess();

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(jwtUtil.extractUsername(validToken)).thenReturn("admin");
        when(jwtUtil.extractUserRole(validToken)).thenReturn("ADMIN");
        when(inventoryService.updateItemsInInventory(anyString(), any(UpdateInventoryItemsPayload.class), anyString())).thenReturn(mockResponse);

        mockMvc.perform(put("/v1/update/inventory/{itemId}", itemId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateInventory_MissingAuthorizationHeader() throws Exception {
        String itemId = "ITEM001";
        UpdateInventoryItemsPayload payload = new UpdateInventoryItemsPayload();

        mockMvc.perform(put("/v1/update/inventory/{itemId}", itemId)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is required", result.getResolvedException().getMessage()));
    }

    @Test
    void testUpdateInventory_Unauthorised() throws Exception {
        String itemId = "ITEM001";
        String invalidToken = "invalidToken";
        String bearerToken = "Bearer " + invalidToken;
        UpdateInventoryItemsPayload payload = new UpdateInventoryItemsPayload();

        when(jwtUtil.validateToken(invalidToken)).thenReturn(false);

        mockMvc.perform(put("/v1/update/inventory/{itemId}", itemId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User token is not valid", result.getResolvedException().getMessage()));
    }

    @Test
    void testUpdateInventory_UnauthorizedRole() throws Exception {
        String itemId = "ITEM001";
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;
        UpdateInventoryItemsPayload payload = new UpdateInventoryItemsPayload();

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(jwtUtil.extractUserRole(validToken)).thenReturn("USER");

        mockMvc.perform(put("/v1/update/inventory/{itemId}", itemId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedException))
                .andExpect(result -> assertEquals("User doest not previlage to perform action", result.getResolvedException().getMessage()));
    }

    @Test
    void testUpdateInventory_NotImplemented() throws Exception {
        String itemId = "ITEM001";
        String validToken = "validToken";
        String bearerToken = "Bearer " + validToken;
        UpdateInventoryItemsPayload payload = new UpdateInventoryItemsPayload();

        mockMvc.perform(put("/v1/update/inventory/{itemId}", itemId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isNotImplemented());
    }

    @Configuration
    static class CustomDateConfig implements WebMvcConfigurer {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
            registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        }
    }

    private CreateUserPayload createUser() {
        CreateUserPayload user = new CreateUserPayload();
        user.setUserName("STF098");
        user.setUserRole("STAFF");
        user.setPassword("Staff@132");
        user.setFirstName("Staff");
        user.setMsisdn("1234567890");
        user.setEmailId("staff@comviva.com");
        return user;
    }

    private ItemDetails createItemDetails(){
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setItemName("Smartphone");
        itemDetails.setPrice(100.0f);
        itemDetails.setQuantity(10.0f);
        itemDetails.setUnits(2);
        return itemDetails;
    }

    private InventoryItemDetailsPayload createInventoryPayload(){
        InventoryItemDetailsPayload payload = new InventoryItemDetailsPayload();
        payload.setCategoryName(InventoryItemDetailsPayload.CategoryNameEnum.DAIRY);
        payload.setItemType(Dairy.CHEESE);
        ItemDetails itemDetails = createItemDetails();
        itemDetails.setDescription("Latest model");
        payload.setItemDetails(Collections.singletonList(itemDetails));
        return payload;
    }

    private SellItemsPayload sellItemsPayload(){
        SellItemsPayload sellItemsPayload = new SellItemsPayload();
        List<SellItemsPayloadItems> sellItemsPayloadItems = new ArrayList<>();
        SellItemsPayloadItems sellItemsPayloadItems1 = new SellItemsPayloadItems();
        sellItemsPayloadItems1.setItemId("ITM009");
        sellItemsPayloadItems1.setUnits(6);
        sellItemsPayloadItems.add(sellItemsPayloadItems1);
        sellItemsPayload.setItems(sellItemsPayloadItems);
        return sellItemsPayload;
    }
}
