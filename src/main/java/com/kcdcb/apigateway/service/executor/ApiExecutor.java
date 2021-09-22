package com.kcdcb.apigateway.service.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.http.HttpServletRequest;

public interface ApiExecutor
{
    ResponseEntity execute(HttpServletRequest httpServletRequest, String user, ProxyExchange<?> proxy) throws HttpRequestMethodNotSupportedException, JsonProcessingException;
}
