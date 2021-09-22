package com.kcdcb.apigateway.exception.error;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;

@Getter
@Slf4j
public class ErrorResponse {
    public ErrorResponse(KcdCbError kcdCbError) {
        this.ErrorCode = kcdCbError.getErrorCode();
        this.ErrorMessage = kcdCbError.getErrorMessage();
    }

    public ErrorResponse(BindException e) {
        this.ErrorCode = "BindError";
        this.ErrorMessage = e.getFieldError().getField() + " " + e.getFieldError().getDefaultMessage();
    }

    private String ErrorCode;
    private String ErrorMessage;
}
