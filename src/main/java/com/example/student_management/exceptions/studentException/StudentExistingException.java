package com.example.student_management.exceptions.studentException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentExistingException extends RuntimeException{
    public StudentExistingException(String exception) {super(exception);}
}
