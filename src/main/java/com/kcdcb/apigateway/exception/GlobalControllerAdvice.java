package com.kcdcb.apigateway.exception;

import com.kcdcb.apigateway.exception.error.ErrorResponse;
import com.kcdcb.apigateway.exception.error.KcdCbError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> resAccessExHandler (ResourceAccessException e) {
        log.error(e.getClass().getName(), e);
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(KcdCbError.HTTP_PROXY_EXCEPTION), HttpStatus.BAD_GATEWAY
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpReqMethodNotSupportExHandler(HttpRequestMethodNotSupportedException e) {
        log.error(e.getClass().getName(), e);
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(KcdCbError.HTTP_NOT_SUPPORTED_METHOD_EXCEPTION), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindExHandler(BindException e) {
        log.error(e.getClass().getName(), e);
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(KcdCbException.class)
    public ResponseEntity<ErrorResponse> kcdCbExHandler(KcdCbException e) {
        log.error(e.getClass().getName(), e);
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getKcdCbError()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exHandler(Exception e) {
        log.error(e.getClass().getName(), e);
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(KcdCbError.SYSTEM_EXCEPTION), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
