package com.kcdcb.apigateway.exception;

import com.kcdcb.apigateway.exception.error.KcdCbError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KcdCbException extends Exception{
    private KcdCbError kcdCbError;
}
