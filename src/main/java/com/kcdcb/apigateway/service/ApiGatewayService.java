package com.kcdcb.apigateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kcdcb.apigateway.service.executor.ApiExecutor;
import com.kcdcb.apigateway.service.executor.ApiExecutorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ApiGatewayService {

    private final ApiExecutorFactory apiExecutorFactory;

    public ResponseEntity doProxy(HttpServletRequest httpServletRequest, String user, ProxyExchange<?> proxy) throws HttpRequestMethodNotSupportedException, JsonProcessingException {
        ApiExecutor executor = apiExecutorFactory.getExecutor(httpServletRequest, user);
        return executor.execute(httpServletRequest, user, proxy);
    }
}
