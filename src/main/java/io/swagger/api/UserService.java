package io.swagger.api;

import io.swagger.entity.Users;
import io.swagger.exception.*;
import io.swagger.model.*;
import io.swagger.repository.UserRepository;
import io.swagger.util.JwtUtil;
import io.swagger.util.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService implements Constants {

    @Autowired
    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    private ResponseUtil responseUtil;

    @Autowired
    public UserService(JwtUtil jwtUtil, ResponseUtil responseUtil, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.responseUtil = responseUtil;
    }

    /**
     * Authenticate Authorization for login.
     *
     * @param authorization Basic auth credentials.
     * @return true if authentication is successful, false otherwise.
     */
    public Boolean loginAuth(String authorization){
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);
            if(values[0].equals("Admin") && values[1].equals("Password")) return true;
            else return false;
        }
        return false;
    }

    /**
     * Handles user login.
     *
     * @param body User login payload.
     * @return Login success response.
     * @throws ApiException if login fails.
     */
    public LoginSucess userLogin( UserLogin body) throws ApiException {
        Users login = userRepository.findByUserName(body.getUserName());
        if(login!= null ) {
            if ( login.getPassword().equals(body.getPassword()) && login.getUserName().equals(body.getUserName())){
                if (login.getUserRole().equals(body.getUserRole())) {
                    if (login.getStatus().equals(ACTIVE)) {
                        String token = jwtUtil.generateToken(body.getUserName(), body.getUserRole(), login.getStatus());
                        //Update login time for user
                        String timeStamp = responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss");
                        int rowsAffected = userRepository.updateUserLoginTime(body.getUserName(), timeStamp);
                        if (rowsAffected == 1) {
                            LoginSucess response = new LoginSucess();
                            response.setUserId(login.getUserId());
                            response.setStatus(SUCCEEDED);
                            response.setAccessToken(token);
                            response.setMessage("Login successful");
                            response.setTransactionTimeStamp(timeStamp);
                            return response;
                        }
                        throw new ApplicationErrorException(HttpStatus.BAD_REQUEST.value(), "Unable to update Data");
                    }
                    throw new UserNotActiveException(HttpStatus.BAD_REQUEST.value(), "User status is not active");
                }
                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "User Role is invalid");
            }
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid login credentials");
        }
        throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "User not Found");
    }

    /**
     * Handles user logout.
     *
     * @param body          User logout payload.
     * @param authorization JWT token.
     * @return Logout success response.
     * @throws ApiException if logout fails.
     */
    public LogoutSucess userLogout(@Valid UserLogout body, String authorization) throws ApiException {
        Users logout = userRepository.findByUserName(body.getUserName());
        if(logout != null) {
            if(jwtUtil.extractUsername(authorization).equals(body.getUserName())){
                LogoutSucess response = new LogoutSucess();
                response.setUserName(body.getUserName());
                response.setStatus(SUCCEEDED);
                response.setMessage("LogOut successful");
                response.setTransactionTimeStamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
                return response;
            }
            throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User does not have privilege to perform action");
        }
        throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "User is not present with user name" +
                body.getUserName());
    }

    /**
     * Creates a new user.
     *
     * @param body          User creation payload.
     * @param authorization JWT token.
     * @return User creation success response.
     * @throws ApiException if user creation fails.
     */
    @Transactional
    public CreateUserSuccess createUser(@Valid @RequestBody CreateUserPayload body, String authorization) throws ApiException {
        if(jwtUtil.extractUserRole(authorization).equals("ADMIN")){
            String userId = "US."+System.currentTimeMillis();

            // Check if the username is already taken
            if (userRepository.findByUserName(body.getUserName()) != null) {
                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Username already exists: " + body.getUserName());
            }

            // Check if the email is already registered
            if (userRepository.findByEmailId(body.getEmailId()) != null) {
                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Email already registered: " + body.getEmailId());
            }

            // Check if the mobile number is already registered
            if (userRepository.findByMsisdn(body.getMsisdn()) != null) {
                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Mobile number already registered: " + body.getMsisdn());
            }

            if (body.getUserRole().equals("ADMIN")&&!jwtUtil.extractUsername(authorization).equals("MAS123")) {
                throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User does not have privilege to perform action");
            }else{
                Users user = new Users();
                user.setUserId(userId);
                user.setUserName(body.getUserName());
                user.setPassword(body.getPassword());
                user.setUserRole(body.getUserRole());
                user.setFirstName(body.getFirstName());
                user.setLastName(body.getLastName());
                user.setMsisdn(body.getMsisdn());
                user.setEmailId(body.getEmailId());
                if (body.getDob()!=null){
                    user.setDob(Date.valueOf(body.getDob()));
                }
                userRepository.save(user);
            }
            CreateUserSuccess response = new CreateUserSuccess();
            response.setMessage("Successfully Onboarded user");
            response.setStatus(SUCCEEDED);
            response.setTransactionTimeStamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
            response.setServiceRequestId(responseUtil.generateRequestId());
            response.setUserName(body.getUserName());
            response.setUserId(userId);
            return response;
        }
        throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User does not have privilege to perform action");
    }

    /**
     * Deletes a user.
     *
     * @param userId User ID to be deleted.
     * @return User deletion success response.
     * @throws ApiException if user deletion fails.
     */
    @Transactional
    public DeleteUserSuccess deleteUser(@NotNull @NotEmpty @Pattern(regexp="^[A-Z]{2}\\.\\d{13}$") @Valid @RequestParam(value = "userId", required = true)String userId) throws ApiException {
        Optional<Users> user = userRepository.findById(userId);
        user.orElseThrow(() -> new NotFoundException(HttpStatus.BAD_REQUEST.value(), "User not found with id: " + userId));
        //Checking if user status is active
        if (user.get().getStatus().equals(INACTIVE)){
            throw new UserNotActiveException(HttpStatus.BAD_REQUEST.value(), "User not found with id: " + userId);
        }
        //Checking is userId belongs to masterAdmin
        if(user.get().getUserName().equals("MAS123")){
            throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User does not have privilege to perform action");
        }
        user.get().setStatus(INACTIVE);
        userRepository.save(user.get());
        DeleteUserSuccess response = new DeleteUserSuccess();
        response.setMessage("Successfully deleted user details");
        response.setServiceRequestId(responseUtil.generateRequestId());
        response.setStatus(SUCCEEDED);
        response.setTransactionTimeStamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
        response.setUserId(userId);
        return response;
    }

    /**
     * Fetches a list of users.
     *
     * @param userId        User ID for fetching specific user details.
     * @param authorization JWT token.
     * @return Fetch user details success response.
     * @throws ApiException if fetching user list fails.
     */
    public FetchUserDetailsSucess fetchUserList(@Pattern(regexp="^[A-Z]{2}\\.\\d{13}$", message = "Invalid userId") @Valid @RequestParam(value = "userId", required = false) String userId, String authorization) throws ApiException{
        String userRole = jwtUtil.extractUserRole(authorization);
        List<AllOfFetchUserDetailsSucessUserDetailsItems> userList = new ArrayList<>();
        if(userId != null){
            if (userRole.equals("ADMIN")){
                Optional<Users> user = userRepository.findById(userId);
                user.orElseThrow(() -> new NotFoundException(HttpStatus.BAD_REQUEST.value(), "User not found with id: " + userId));
                List<Users> users = new ArrayList<>();
                users.add(user.get());
                userList = appendUserValues(users);
            }else {
                Optional<Users> user = userRepository.findById(userId);
                user.orElseThrow(() -> new NotFoundException(HttpStatus.BAD_REQUEST.value(), "User not found with id: " + userId));
                if(!user.get().getUserRole().equals("STAFF")){
                    throw new UnauthorizedException(HttpStatus.BAD_REQUEST.value(), "User is unauthorised to fetch details");
                }else {
                    List<Users> users = new ArrayList<>();
                    users.add(user.get());
                    userList = appendUserValues(users);
                }
            }
        } else if (userRole.equals("ADMIN")){
            Optional<List<Users>> users = Optional.of(userRepository.findAll());
            users.orElseThrow(() -> new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Users are not present"));
            userList = appendUserValues(users.get());
        } else if (userRole.equals("STAFF")){
            Optional<List<Users>> users = Optional.of(userRepository.findByUserRole(userRole));
            users.orElseThrow(() -> new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Users are not present"));
            userList = appendUserValues(users.get());
        }
        if (userList.size()==0){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Users are not present");
        }
        FetchUserDetailsSucess response = new FetchUserDetailsSucess();
        response.setUserDetails(userList);
        response.setMessage("Successfully fetched user details");
        response.setServiceRequestId(responseUtil.generateRequestId());
        response.setStatus(SUCCEEDED);
        response.setTransactionTimeStamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return response;
    }

    /**
     * Appends user values to a list of user detail items.
     *
     * @param usersList List of users.
     * @return List of user detail items.
     */
    public List<AllOfFetchUserDetailsSucessUserDetailsItems> appendUserValues(List<Users> usersList){
        return usersList.stream()
                .filter(Objects::nonNull)
                .filter(user -> "ACTIVE".equals(user.getStatus()))
                .map(user -> {
                    AllOfFetchUserDetailsSucessUserDetailsItems userDetails = new AllOfFetchUserDetailsSucessUserDetailsItems();
                    userDetails.setFirstName(user.getFirstName());
                    userDetails.setLastName(user.getLastName());
                    userDetails.setEmailId(user.getEmailId());
                    userDetails.setDob(String.valueOf(user.getDob()));
                    userDetails.setUserId(user.getUserId());
                    userDetails.setMsisdn(user.getMsisdn());
                    userDetails.setStatus(user.getStatus());
                    userDetails.setUserName(user.getUserName());
                    userDetails.setUserRole(user.getUserRole());
                    return userDetails;
                })
                .collect(Collectors.toList());
    }

}
