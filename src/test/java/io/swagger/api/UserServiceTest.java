package io.swagger.api;

import io.swagger.entity.Users;
import io.swagger.exception.ApiException;
import io.swagger.exception.NotFoundException;
import io.swagger.exception.UnauthorizedException;
import io.swagger.exception.UserNotActiveException;
import io.swagger.model.*;
import io.swagger.repository.UserRepository;
import io.swagger.util.JwtUtil;
import io.swagger.util.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ResponseUtil responseUtil;

    private final String txnTimeStamp = "2024-05-30T12:00:00";

    private final String serviceRequestId = "abfae6a5-7320-4e3a-b3f2-93d2774fcce1";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginAuth_ValidCredentials() {
        String authorization = "Basic QWRtaW46UGFzc3dvcmQ="; // Base64 encoded "Admin:Password"
        boolean result = userService.loginAuth(authorization);
        assertTrue(result);
    }

    @Test
    void testLoginAuth_InvalidCredentials() {
        String authorization = "Basic dXNlcjpwYXNzd29yZA=="; // Base64 encoded "user:password"
        boolean result = userService.loginAuth(authorization);
        assertFalse(result);
    }

    @Test
    void testLoginAuth_NullAuthorization() {
        boolean result = userService.loginAuth(null);
        assertFalse(result);
    }

    @Test
    void testLoginAuth_NoBasicAuthorization() {
        String authorization = "Bearer token"; // Authorization type other than Basic
        boolean result = userService.loginAuth(authorization);
        assertFalse(result);
    }

    @Test
    void testCreateUser_Admin_Success() throws ApiException {
        // Given
        CreateUserPayload payload = new CreateUserPayload();
        payload.dob("2002-12-02");
        String authorization = "Bearer " + getMockAdminToken();
        mockCommonUserRepositoryFinds();
        mockCommonJwtUtils(authorization,"ADMIN", "ADMIN");
        when(userRepository.save(any(Users.class))).thenReturn(new Users());
        mockResponseUtil();

        // When
        CreateUserSuccess result = userService.createUser(payload, authorization);

        // Then
        assertNotNull(result);
        assertEquals("Successfully Onboarded user", result.getMessage());
        assertNotNull(result.getUserId());
        assertEquals(payload.getUserName(), result.getUserName());
        assertCommonResponseParams(result.getTransactionTimeStamp(), result.getServiceRequestId(),  String.valueOf(result.getStatus()));
    }

    @Test
    void testCreateUser_Admin_ByAnotherAdmin(){
        // Given
        CreateUserPayload payload = new CreateUserPayload();
        payload.userRole(CreateUserPayload.UserRoleEnum.ADMIN);
        String authorization = getMockAdminToken();

        mockCommonJwtUtils(authorization,"ADMIN", "ADM123");

        // When & Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            userService.createUser(payload, authorization);
        });

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User does not have privilege to perform action"));
    }

    @Test
    void testCreateUser_Admin_UsernameExists() {
        // Given
        CreateUserPayload payload = new CreateUserPayload();
        String authorization = "Bearer " + getMockAdminToken();

        when(jwtUtil.extractUserRole(authorization)).thenReturn("ADMIN");
        when(userRepository.findByUserName(null)).thenReturn(new Users());

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.createUser(payload, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Username already exists"));
    }

    @Test
    void testCreateUser_Admin_EmailExists() {
        // Given
        CreateUserPayload payload = new CreateUserPayload();
        String authorization = "Bearer " + getMockAdminToken();

        when(jwtUtil.extractUserRole(authorization)).thenReturn("ADMIN");
        when(userRepository.findByUserName(null)).thenReturn(null);
        when(userRepository.findByEmailId(null)).thenReturn(new Users());

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.createUser(payload, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Email already registered"));
    }

    @Test
    void testCreateUser_Admin_MobileExists() {
        // Given
        CreateUserPayload payload = new CreateUserPayload();
        String authorization = "Bearer " + getMockAdminToken();

        when(jwtUtil.extractUserRole(authorization)).thenReturn("ADMIN");
        when(userRepository.findByUserName(null)).thenReturn(null);
        when(userRepository.findByEmailId(null)).thenReturn(null);
        when(userRepository.findByMsisdn(null)).thenReturn(new Users());

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.createUser(payload, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Mobile number already registered"));
    }

    @Test
    void testCreateUser_Admin_InvalidUserRole() {
        // Given
        CreateUserPayload payload = new CreateUserPayload();
        String authorization = "Bearer " + getMockAdminToken();

        when(jwtUtil.extractUserRole(authorization)).thenReturn("USER");

        // When & Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            userService.createUser(payload, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User does not have privilege to perform action"));
    }

    @Test
    void testCreateUser_NonAdmin_Unauthorized() {
        // Given
        CreateUserPayload payload = new CreateUserPayload();
        String authorization = "Bearer " + getMockUserToken();

        when(jwtUtil.extractUserRole(authorization)).thenReturn("USER");

        // When & Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            userService.createUser(payload, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User does not have privilege to perform action"));
    }

    @Test
    void testCreateUser_Admin_Unauthorized() {
        // Given
        CreateUserPayload payload = new CreateUserPayload();
        payload.setUserRole("ADMIN");
        String authorization = "Bearer " + getMockUserToken();

        when(jwtUtil.extractUserRole(authorization)).thenReturn("ADMIN");
        when(jwtUtil.extractUsername(authorization)).thenReturn("ADM123");

        // When & Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            userService.createUser(payload, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User does not have privilege to perform action"));
    }

    @Test
    void testUserLogin_Success() throws ApiException {
        UserLogin loginRequest = createLoginRequest("ADB13","ADMIN","password");
        Users user = createUser("ADB13","ADMIN", "password","ACTIVE");

        // Mock database behavior
        when(userRepository.findByUserName("ADB13")).thenReturn(user);
        when(jwtUtil.generateToken("ADB13", "ADMIN", "ACTIVE")).thenReturn("mockToken");
        mockResponseUtil();
        when(userRepository.updateUserLoginTime("ADB13", txnTimeStamp)).thenReturn(1);

        // When
        LoginSucess response = userService.userLogin(loginRequest);

        // Then
        assertNotNull(response);
        assertEquals(user.getUserId(), response.getUserId());
        assertEquals("SUCCEEDED", String.valueOf(response.getStatus()));
        assertNotNull(response.getAccessToken());
        assertEquals("Login successful", response.getMessage());
        assertEquals(txnTimeStamp, response.getTransactionTimeStamp());
    }

    @Test
    void testUserLogin_UserNotFound() {
        // Given
        UserLogin loginRequest = new UserLogin();
        when(userRepository.findByUserName(null)).thenReturn(null);

        // When & Then
        ApiException exception = assertThrows(NotFoundException.class, () -> {
            userService.userLogin(loginRequest);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User not Found"));
    }

    @Test
    void testUserLogin_InvalidCredentials() {
        // Given
        UserLogin loginRequest = new UserLogin();
        loginRequest.setPassword("1234");
        Users user = new Users();
        user.setPassword("qwer");
        user.setStatus("ACTIVE");
        // Mock database behavior
        when(userRepository.findByUserName(null)).thenReturn(user);

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.userLogin(loginRequest);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Invalid login credentials"));
    }

    @Test
    void testUserLogin_InactiveUser() {
        // Given
        UserLogin loginRequest = new UserLogin();
        loginRequest.setPassword("1234");
        loginRequest.setUserName("staff");
        loginRequest.setUserRole("STAFF");
        Users user = new Users();
        user.setPassword("1234");
        user.setUserName("staff");
        user.setUserRole("STAFF");
        // Populate user with necessary details
        user.setStatus("INACTIVE");
        // Mock database behavior
        when(userRepository.findByUserName("staff")).thenReturn(user);

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.userLogin(loginRequest);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User status is not active"));
    }

    @Test
    void testUserLogin_InvalidUserRole() {
        // Given
        UserLogin loginRequest = new UserLogin();
        loginRequest.setPassword("1234");
        loginRequest.setUserName("staff");
        loginRequest.setUserRole("STAFF");
        Users user = new Users();
        user.setPassword("1234");
        user.setUserName("staff");
        user.setUserRole("ADMIN");
        user.setStatus("ACTIVE");
        // Mock database behavior
        when(userRepository.findByUserName("staff")).thenReturn(user);

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.userLogin(loginRequest);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User Role is invalid"));
    }

    @Test
    void testUserLogin_UnableToUpdateData() {
        // Given
        UserLogin loginRequest = new UserLogin();
        loginRequest.setPassword("1234");
        loginRequest.setUserName("staff");
        loginRequest.setUserRole("STAFF");
        Users user = new Users();
        user.setPassword("1234");
        user.setUserName("staff");
        user.setUserRole("STAFF");
        user.setStatus("ACTIVE");
        // Mock database behavior
        when(userRepository.findByUserName("staff")).thenReturn(user);
        when(userRepository.updateUserLoginTime("staff", null)).thenReturn(0);

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.userLogin(loginRequest);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Unable to update Data"));
    }

    @Test
    void testUserLogout_Success() throws ApiException {
        // Given
        UserLogout logoutRequest = new UserLogout();
        logoutRequest.setUserName("Staff");
        String authorization = "Bearer mockToken";
        Users user = new Users();
        user.setUserName(logoutRequest.getUserName());
        // Mock database behavior
        when(userRepository.findByUserName("Staff")).thenReturn(user);
        when(jwtUtil.extractUsername(authorization)).thenReturn(logoutRequest.getUserName());
        mockResponseUtil();

        // When
        LogoutSucess response = userService.userLogout(logoutRequest, authorization);

        // Then
        assertNotNull(response);
        assertEquals(logoutRequest.getUserName(), response.getUserName());
        assertEquals("SUCCEEDED", String.valueOf(response.getStatus()));
        assertEquals("LogOut successful", response.getMessage());
        assertEquals(txnTimeStamp, response.getTransactionTimeStamp());
    }

    @Test
    void testUserLogout_Unauthorized() {
        // Given
        UserLogout logoutRequest = new UserLogout();
        logoutRequest.setUserName("Staff");
        String authorization = "Bearer mockToken";
        Users user = new Users();
        user.setUserName("DifferentUser");
        // Mock database behavior
        when(userRepository.findByUserName(anyString())).thenReturn(user);
        when(jwtUtil.extractUsername(authorization)).thenReturn(user.getUserName());

        // When & Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            userService.userLogout(logoutRequest, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User does not have privilege to perform action"));
    }

    @Test
    void testUserLogout_InvalidCredentials() {
        // Given
        UserLogout logoutRequest = new UserLogout();
        String authorization = "Bearer mockToken";
        // Mock database behavior
        when(userRepository.findByUserName(null)).thenReturn(null);

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.userLogout(logoutRequest, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Invalid credentials"));
    }

    @Test
    void testDeleteUser_Success() throws ApiException {
        // Given
        String userId = "US.1234567890123";
        Users user = new Users();
        user.setUserName("Staff");
        user.setStatus("ACTIVE");
        // Mock database behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        mockResponseUtil();

        // When
        DeleteUserSuccess response = userService.deleteUser(userId);

        // Then
        assertNotNull(response);
        assertEquals("Successfully deleted user details", response.getMessage());
        assertEquals(userId, response.getUserId());
        assertCommonResponseParams(response.getTransactionTimeStamp(), response.getServiceRequestId(),  String.valueOf(response.getStatus()));
    }

    @Test
    void testDeleteUser_UserNotFound() {
        // Given
        String userId = "US.1234567890123";
        // Mock database behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.deleteUser(userId);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User not found with id: " + userId));
    }

    @Test
    void testDeleteUser_UserInactive() {
        // Given
        String userId = "US.1234567890123";
        Users user = new Users();
        user.setStatus("INACTIVE");
        // Mock database behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When & Then
        UserNotActiveException exception = assertThrows(UserNotActiveException.class, () -> {
            userService.deleteUser(userId);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User not found with id: " + userId));
    }

    @Test
    void testDeleteUser_DeleteMasterAdmin() {
        // Given
        String userId = "US.1234567890123";
        Users user = new Users();
        user.setStatus("ACTIVE");
        user.setUserName("MAS123");
        // Mock database behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When & Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            userService.deleteUser(userId);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User does not have privilege to perform action"));
    }

    @Test
    void testFetchUserList_FetchUserById_Admin_Success() throws ApiException {
        // Given
        String userId = "US.1234567890123";
        String authorization = "Bearer mockAdminToken";
        Users user = new Users(userId,"Master", "Admin", "masterAdmin@gcomviva.com", "Inventory@123", "9988776655", null, "MAS123", "ADMIN");

        // Mock database behavior
        when(jwtUtil.extractUserRole(authorization)).thenReturn("ADMIN");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        mockResponseUtil();

        // When
        FetchUserDetailsSucess response = userService.fetchUserList(userId, authorization);

        // Then
        assertNotNull(response);
        assertEquals("Successfully fetched user details", response.getMessage());
        assertNotNull(response.getUserDetails());
        assertEquals(1, response.getUserDetails().size());
        assertEquals(userId, response.getUserDetails().get(0).getUserId());
        assertCommonResponseParams(response.getTransactionTimeStamp(), response.getServiceRequestId(),  String.valueOf(response.getStatus()));
    }

    @Test
    void testFetchUserList_FetchUserById_Staff_Success() throws ApiException {
        // Given
        String userId = "US.1234567890123";
        String authorization = "Bearer mockStaffToken";
        Users user = new Users(userId,"Master", "Admin", "masterAdmin@gcomviva.com", "Inventory@123", "9988776655", null, "MAS123", "STAFF");
        user.setUserId(userId);

        // Mock database behavior
        when(jwtUtil.extractUserRole(authorization)).thenReturn("STAFF");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        mockResponseUtil();

        // When
        FetchUserDetailsSucess response = userService.fetchUserList(userId, authorization);

        // Then
        assertNotNull(response);
        assertEquals("Successfully fetched user details", response.getMessage());
        assertNotNull(response.getUserDetails());
        assertEquals(1, response.getUserDetails().size());
        assertEquals(userId, response.getUserDetails().get(0).getUserId());
        assertCommonResponseParams(response.getTransactionTimeStamp(), response.getServiceRequestId(),  String.valueOf(response.getStatus()));
    }

    @Test
    void testFetchUserList_FetchAllUsers_Admin_Success() throws ApiException {
        // Given
        String authorization = getMockAdminToken();
        Users user1 = new Users("US.9876543210984","master", "admin", "masteradmin@gcomviva.com", "Inventory@123", "9988876655", null, "MAS223", "STAFF");
        Users user2 = new Users("US.9876543210987","Master", "Admin", "masterAdmin@gcomviva.com", "Inventory@123", "9988776655", null, "MAS123", "STAFF");

        List<Users> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);

        // Mock database behavior
        when(jwtUtil.extractUserRole(authorization)).thenReturn("ADMIN");
        when(userRepository.findAll()).thenReturn(usersList);
        mockResponseUtil();

        // When
        FetchUserDetailsSucess response = userService.fetchUserList(null, authorization);

        // Then
        assertNotNull(response);
        assertEquals("Successfully fetched user details", response.getMessage());
        assertNotNull(response.getUserDetails());
        assertEquals(2, response.getUserDetails().size());
        assertCommonResponseParams(response.getTransactionTimeStamp(), response.getServiceRequestId(),  String.valueOf(response.getStatus()));
    }

    @Test
    void testFetchUserList_FetchAllUsers_Staff_Success() throws ApiException {
        // Given
        String authorization = getMockUserToken();
        Users user1 = new Users("US.9876543210984","master", "admin", "masteradmin@gcomviva.com", "Inventory@123", "9988876655", null, "MAS223", "STAFF");
        Users user2 = new Users("US.9876543210987","Master", "Admin", "masterAdmin@gcomviva.com", "Inventory@123", "9988776655", null, "MAS123", "STAFF");

        List<Users> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);

        // Mock database behavior
        when(jwtUtil.extractUserRole(authorization)).thenReturn("STAFF");
        when(userRepository.findByUserRole("STAFF")).thenReturn(usersList);
        mockResponseUtil();

        // When
        FetchUserDetailsSucess response = userService.fetchUserList(null, authorization);

        // Then
        assertNotNull(response);
        assertEquals("Successfully fetched user details", response.getMessage());
        assertNotNull(response.getUserDetails());
        assertEquals(2, response.getUserDetails().size());
        assertCommonResponseParams(response.getTransactionTimeStamp(), response.getServiceRequestId(),  String.valueOf(response.getStatus()));
    }

    @Test
    void testFetchUserList_UnauthorizedAccess() {
        // Given
        String userId = "US.1234567890123";
        String authorization = getMockUserToken();
        Users user = new Users();
        user.setUserRole("ADMIN");

        // Mock database behavior
        when(jwtUtil.extractUserRole(authorization)).thenReturn("STAFF");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When & Then
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            userService.fetchUserList(userId, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User is unauthorised to fetch details"));
    }

    @Test
    void testFetchUserList_NoUsersPresent_Admin() {
        // Given
        String authorization = getMockAdminToken();

        // Mock database behavior
        when(jwtUtil.extractUserRole(authorization)).thenReturn("ADMIN");
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.fetchUserList(null, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Users are not present"));
    }

    @Test
    void testFetchUserList_NoUsersPresent_Staff() {
        // Given
        String authorization = getMockUserToken();

        // Mock database behavior
        when(jwtUtil.extractUserRole(authorization)).thenReturn("STAFF");
        when(userRepository.findByUserRole("STAFF")).thenReturn(new ArrayList<>());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.fetchUserList(null, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Users are not present"));
    }

    @Test
    void testFetchUserList_UserNotFound() {
        // Given
        String userId = "US.1234567890123";
        String authorization = getMockAdminToken();

        // Mock database behavior
        when(jwtUtil.extractUserRole(authorization)).thenReturn("ADMIN");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.fetchUserList(userId, authorization);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertTrue(exception.getMessage().contains("User not found with id: " + userId));
    }

    private UserLogin createLoginRequest(String username, String role, String password) {
        UserLogin loginRequest = new UserLogin();
        loginRequest.setUserName(username);
        loginRequest.setUserRole(role);
        loginRequest.setPassword(password);
        return loginRequest;
    }

    private Users createUser(String username, String role, String password, String status) {
        Users user = new Users();
        user.setUserName(username);
        user.setUserRole(role);
        user.setPassword(password);
        user.setStatus(status);
        return user;
    }

    private String getMockAdminToken() {
        String mockAdminToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQVMxMjMiLCJ1c2VyU3RhdHVzIjoiQUNUSVZFIiwidXNlclJvbGUiOiJBRE1JTiIsImV4cCI6MTcxNjYyNjAzOSwiaWF0IjoxNzE2NjI0MjM5fQ.HgtUUDFPOyOjMvbHgWQQ9C6ND8qjNGLFGl94kcWkHso";
        return mockAdminToken;
    }

    private String getMockUserToken() {
        String mockUserToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE03NTMiLCJ1c2VyU3RhdHVzIjoiQUNUSVZFIiwidXNlclJvbGUiOiJTVEFGRiIsImV4cCI6MTcxNzEzMDU2NCwiaWF0IjoxNzE3MTI4NzY0fQ.ioZzhTXxPYhsdPN5f-JpZIAgfhLooNpsTm9acjVxzmA";
        return mockUserToken;
    }

    private void mockCommonUserRepositoryFinds() {
        when(userRepository.findByUserName(null)).thenReturn(null);
        when(userRepository.findByEmailId(null)).thenReturn(null);
        when(userRepository.findByMsisdn(null)).thenReturn(null);
    }

    private void mockCommonJwtUtils(String authorization, String username, String role) {
        lenient().when(jwtUtil.extractUsername(authorization)).thenReturn(username);
        lenient().when(jwtUtil.extractUserRole(authorization)).thenReturn(role);
    }

    private void mockResponseUtil() {
        lenient().when(responseUtil.generateRequestId()).thenReturn(serviceRequestId);
        lenient().when(responseUtil.formatDateInGivenFormat(anyString())).thenReturn(txnTimeStamp);
    }

    private void assertCommonResponseParams(String timeStamp, String requestId, String status){
        assertEquals(txnTimeStamp, timeStamp);
        assertEquals(serviceRequestId, requestId);
        assertEquals("SUCCEEDED", status);
    }


}
