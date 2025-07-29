package com.vighnesh.library.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LogicalError extends RuntimeException {
    public LogicalError(String message) {
        super(message);
    }
}