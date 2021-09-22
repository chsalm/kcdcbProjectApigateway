package com.kcdcb.apigateway.dto;

import com.kcdcb.apigateway.Constants;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Getter
@Data
@RequiredArgsConstructor
public class ProxyRequest {
    private final String requestPath;
    private final String httpMethodString;
    private final String kcdcbRequestHeader;
    private final String queryString;
    private final Map parameterMap;

    public static ProxyRequest of(HttpServletRequest httpServletRequest) {
        return new ProxyRequest(
                httpServletRequest.getRequestURI(),
                httpServletRequest.getMethod(),
                httpServletRequest.getHeader(Constants.KCDCB_REQUEST_HEADER),
                httpServletRequest.getQueryString(),
                httpServletRequest.getParameterMap());
    }
}
