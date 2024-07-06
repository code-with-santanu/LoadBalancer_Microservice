package com.santanu.LoadBalancer.exceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {

        Map<String,String> responseObj = new HashMap<>();

        // Bind all the error messages in the responseObj
        exp.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();

            responseObj.put(fieldName,message);
        });


        return new ResponseEntity<Map<String,String>>(responseObj,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        String path = request.getRequestURI();

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        errorResponse.setPath(path);

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // To handle other type of exception
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exp) {
//        // Define custom response object
//        ErrorResponse error = new ErrorResponse();
//
//        // Set the values
//        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        error.setMessage(exp.getMessage());
//        error.setTimeStamp(System.currentTimeMillis());
//
//        // Return the response object
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
