package com.kcdcb.apigateway.service.executor;

import com.kcdcb.apigateway.Constants;
import com.kcdcb.apigateway.config.properties.AppProperties;
import com.kcdcb.apigateway.dto.ProxyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasicApiExecutor implements ApiExecutor{
    private final AppProperties appProperties;
    @Override
    public ResponseEntity execute(HttpServletRequest httpServletRequest, String user, ProxyExchange<?> proxy) throws HttpRequestMethodNotSupportedException {
        ResponseEntity responseEntity;
        String requestPath = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        String proxyURL = appProperties.getDefaultProxyHost() + requestPath;
        proxyURL = proxyURL.replace("/" + user,"");
        proxyURL = proxyURL+"?"+httpServletRequest.getQueryString();
        proxy
                .header(Constants.KCDCB_REQUEST_HEADER,httpServletRequest.getHeader(Constants.KCDCB_REQUEST_HEADER))
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .body(formatQueryParams(httpServletRequest.getParameterMap()))

                        .uri(proxyURL);

        if(HttpMethod.GET.matches(method)) {
            responseEntity = proxy.get();
        } else if(HttpMethod.POST.matches(method)) {
            responseEntity = proxy.post();
        } else {
            throw new HttpRequestMethodNotSupportedException("proxy not supports this http method(it only supports GET/POST)");
        }
        if(!(HttpStatus.OK).equals(responseEntity.getStatusCode())) {
            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        } else {
            return responseEntity;
        }
    }

    public ResponseEntity execute(ProxyRequest proxyRequest, String user, ProxyExchange<?> proxy) throws HttpRequestMethodNotSupportedException {
        ResponseEntity responseEntity;
        String proxyURL = appProperties.getDefaultProxyHost() + proxyRequest.getRequestPath();
        proxyURL = proxyURL.replace("/" + user,"");
        proxyURL = proxyURL+"?" +proxyRequest.getQueryString();
        proxy
                .header(Constants.KCDCB_REQUEST_HEADER, proxyRequest.getKcdcbRequestHeader())
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .body(formatQueryParams(proxyRequest.getParameterMap()))
                .uri(proxyURL);
        if(HttpMethod.GET.matches(proxyRequest.getHttpMethodString())) {
            responseEntity = proxy.get();
        } else if(HttpMethod.POST.matches(proxyRequest.getHttpMethodString())) {
            responseEntity = proxy.post();
        } else {
            throw new HttpRequestMethodNotSupportedException("proxy not supports this http method(it only supports GET/POST)");
        }
        if(!(HttpStatus.OK).equals(responseEntity.getStatusCode())) {
            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        } else {
            return responseEntity;
        }
    }

    private String formatQueryParams(Map<String, String[]> params) {
        return params.entrySet().stream()
                .map(p -> p.getKey() + "=" + p.getValue()[0])
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
    }
}
