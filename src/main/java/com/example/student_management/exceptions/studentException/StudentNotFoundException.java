package com.example.student_management.exceptions.studentException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String exception) {
        super(exception);
    }
}
