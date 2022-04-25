package com.example.student_management.exceptions.falcutyException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FacultyExistingException extends RuntimeException{
    public FacultyExistingException (String exception) {super(exception);}
}
