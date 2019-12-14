package com.scrotifybanking.scrotifybanking.exception;

import org.springframework.http.HttpStatus;

public class ApiError {

    private String message;
    private HttpStatus httpStatus;

    public ApiError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;

    }

    public ApiError() {
    }
}

