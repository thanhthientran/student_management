package com.example.student_management.exceptions;

import java.util.Date;


public class ResponseException {

    private Boolean success;
    private String message;
    private String details;
    private Date timestamp;

    public ResponseException(Boolean success, String message, String details, Date timestamp){
        super();
        this.success=success;
        this.message=message;
        this.details=details;
        this.timestamp=timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Boolean getSuccess(){
        return success;
    }

}
