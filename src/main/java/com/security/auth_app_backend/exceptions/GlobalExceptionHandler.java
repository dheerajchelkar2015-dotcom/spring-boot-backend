package com.security.auth_app_backend.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.security.auth_app_backend.dtos.ApiError;
import com.security.auth_app_backend.dtos.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
        ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(),HttpStatus.NOT_FOUND,404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(internalServerError);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception){
        logger.info("Exception :{}", exception.getClass().getName());
        ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(),HttpStatus.BAD_REQUEST,400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(internalServerError);
    }

    @ExceptionHandler({
        UsernameNotFoundException.class,
        BadCredentialsException.class,
        CredentialsExpiredException.class,
        DisabledException.class
    })
    public ResponseEntity<ApiError> handleAuthException(Exception e,HttpServletRequest request){

        var apiError= ApiError.of(HttpStatus.BAD_REQUEST.value(), "Bad Request", e.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(apiError);

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSize(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .badRequest()
                .body("File too large. Max allowed is 10MB");
    }
    

}
