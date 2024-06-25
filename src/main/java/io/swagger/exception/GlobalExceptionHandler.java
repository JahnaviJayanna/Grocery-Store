package io.swagger.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.swagger.model.ErrorResponse;
import io.swagger.model.ResponseErrors;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValidException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ResponseErrors errorMsg = new ResponseErrors();
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            errorMsg.setCode("invalid." + fieldName);
            errorMsg.setMessage(fieldName+" : "+error.getDefaultMessage());
            errorList.add(errorMsg);
        });
        errors.setErrorUserMsg("Invalid input");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableExceptions(HttpMessageNotReadableException ex) {
        logger.error("HttpMessageNotReadableException ", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        Throwable cause = ex.getCause();
        ResponseErrors errorMsg = new ResponseErrors();
        if (cause instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) cause;
            String fieldName = jsonMappingException.getPath().isEmpty() ? "unknown" : jsonMappingException.getPath().get(0).getFieldName();
            errorMsg.setCode("invalid." + fieldName);
            errorMsg.setMessage("Invalid input for " + fieldName);
        } else {
            errorMsg.setCode("invalid.payload");
            errorMsg.setMessage("Invalid payload");
        }
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Invalid Input");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        logger.error("ExpiredJwtException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("token.invalid");
        errorMsg.setMessage("Token is invalid");
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Token is invalid");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex) {
        logger.error("SignatureException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("token.invalid");
        errorMsg.setMessage("Token is invalid");
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Token is invalid");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("invalid.value");
        errorMsg.setMessage(ex.getMessage());
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Invalid payload");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        logger.error("MissingServletRequestParameterException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("invalid."+ex.getParameterType()+ex.getParameterName());
        errorMsg.setMessage(ex.getMessage());
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Missing Request Parameter");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
        logger.error("NullPointerException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("invalid.payload");
        errorMsg.setMessage(ex.getMessage());
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Invalid Payload");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex) {
        logger.error("MalformedJwtException", ex);
        List<ResponseErrors> errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("invalid.token");
        errorMsg.setMessage("Token is not valid ");
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Invalid JWT token");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("ConstraintViolationException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            ResponseErrors errorMsg = new ResponseErrors();
            errorMsg.setCode("invalid."+propertyPath);
            errorMsg.setMessage(propertyPath + violation.getMessage());
            errorList.add(errorMsg);
        });
        errors.setErrorUserMsg("Invalid value");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiExceptions(ApiException ex) {
        logger.error("ApiException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("invalid.input");
        errorMsg.setMessage(ex.getMessage());
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Invalid Input");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(UserNotActiveException.class)
    public ResponseEntity<ErrorResponse> handleUserNotActiveExceptions(UserNotActiveException ex) {
        logger.error("UserNotActiveException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("invalid.user");
        errorMsg.setMessage(ex.getMessage());
        errorList.add(errorMsg);
        errors.setErrorUserMsg("User is not present");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(NotFoundException ex) {
        logger.error("NotFoundException", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("data.not.found");
        errorMsg.setMessage(ex.getMessage());
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Invalid input");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(ApplicationErrorException.class)
    public ResponseEntity<ErrorResponse> handleApplicationErrorExceptions(ApplicationErrorException ex) {
        logger.error("ApplicationErrorException ", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("error.occurred");
        errorMsg.setMessage(ex.getMessage());
        errorList.add(errorMsg);
        errors.setErrorUserMsg("Application error");
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnprevilagedUserExceptions(UnauthorizedException ex) {
        logger.error("UnauthorizedException ", ex);
        List<ResponseErrors>  errorList = new ArrayList<>();
        ErrorResponse errors = commonErrorResponseFields();
        ResponseErrors errorMsg = new ResponseErrors();
        errorMsg.setCode("user.not.authorised");
        errorMsg.setMessage(ex.getMessage());
        errorList.add(errorMsg);
        errors.setErrorUserMsg(ex.getMessage());
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.valueOf(ex.getStatusCode()));
    }
    private ErrorResponse commonErrorResponseFields (){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        ErrorResponse errors = new ErrorResponse();
        errors.setHttpErrorCode("400");
        errors.setStatus("FAILED");
        errors.setServiceRequestId(UUID.randomUUID().toString());
        errors.setTransactionTimeStamp(LocalDateTime.now().format(formatter));
        return errors;
    }

}
