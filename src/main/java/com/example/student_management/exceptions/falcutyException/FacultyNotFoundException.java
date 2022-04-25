package com.example.student_management.exceptions.falcutyException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException (String exception){ super(exception); }
}
