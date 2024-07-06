package com.santanu.LoadBalancer.controller;

import com.santanu.LoadBalancer.exceptionHandler.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
        Object status = request.getAttribute("jakarta.servlet.error.status_code"); // get status code
        HttpStatus httpStatus = status != null ? HttpStatus.valueOf(Integer.parseInt(status.toString())) : HttpStatus.INTERNAL_SERVER_ERROR; // handling null value for status code
        String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message"); // get the error message
        String path = (String) request.getAttribute("jakarta.servlet.error.request_uri"); // get the uri

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(httpStatus.value());
        errorResponse.setMessage(errorMessage != null ? errorMessage : "Unexpected error occurred");
        errorResponse.setTimeStamp(System.currentTimeMillis());
        errorResponse.setPath(path);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    //    @RequestMapping("/error")
//    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request, HttpServletResponse response) {
//        Object exception = request.getAttribute("jakarta.servlet.error.exception");
//        String path = (String) request.getAttribute("jakarta.servlet.error.request_uri");
//
//        ErrorResponse errorResponse = new ErrorResponse();
//        if (exception instanceof NullPointerException) {
//            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
////            errorResponse.setMessage(((NullPointerException) exception).getMessage());
//            errorResponse.setMessage("No active server is available rn...");
//            errorResponse.setTimeStamp(System.currentTimeMillis());
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }else if (exception instanceof AccessDeniedException) {
//            errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//            errorResponse.setMessage("You are not authorized to access this resource");
//            errorResponse.setTimeStamp(System.currentTimeMillis());
//            errorResponse.setPath(path);
//            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
//        }else {
//            // Handle other types of exceptions or return a generic error response
//            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            errorResponse.setMessage("An unexpected error occurred...");
//            errorResponse.setTimeStamp(System.currentTimeMillis());
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
