package com.example.student_management.exceptions.falcutyException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FacultyNoContentException extends RuntimeException {
    public  FacultyNoContentException (String exception) { super(exception);}
}
