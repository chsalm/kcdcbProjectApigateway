package com.kcdcb.apigateway.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KcdCbError {
    SYSTEM_EXCEPTION("E0001","시스템 에러가 발생하였습니다. 담당자에게 연락 바랍니다."),
    BUSINESS_EXCEPTION("E0002", "에러가 발생하였습니다. 담당자에게 연락 바랍니다."),
    HTTP_NOT_SUPPORTED_METHOD_EXCEPTION("E0003", "요청 HTTP 메서드를 확인해주세요."),
    HTTP_PROXY_EXCEPTION("E0004", "PROXY 에러가 발생하였습니다. 담당자에게 연락 바랍니다.");

    private String errorCode;
    private String errorMessage;
}