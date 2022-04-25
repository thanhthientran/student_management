package com.example.student_management.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity handleUserNotFoundException(StudentNotFoundException ex, WebRequest request) {
        ResponseException exceptionResponse = new ResponseException(false, ex.getMessage(),
                request.getDescription(false),new Date());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request){
        ResponseException exceptionResponse = new ResponseException(
                false,
                "Validation failed"
                ,ex.getBindingResult().toString()
//                ,ex.getMessage()
                ,new Date());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected  ResponseEntity handleAllException(Exception ex,
                                                 WebRequest webRequest){
        ResponseException exceptionResponse = new ResponseException(
                false,
//                "validation failed",
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date());
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

//    @ExceptionHandler(TestException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity handleTestException(TestException ex, WebRequest request){
//        ResponseException responseException = new ResponseException(false,
//                                                                    ex.getMessage(),
//                                                                    request.getDescription(false),
//                                                                    new Date());
//        return new ResponseEntity(responseException,HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(TestExceptionNull.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity handleTestExceptionNull(TestExceptionNull ex, WebRequest req){
//        ResponseException responseException = new ResponseException(
//                false,
//                ex.getMessage(),
//                req.getDescription(false),
//                new Date()
//        );
//        return new ResponseEntity(responseException, HttpStatus.BAD_REQUEST);
//    }

}