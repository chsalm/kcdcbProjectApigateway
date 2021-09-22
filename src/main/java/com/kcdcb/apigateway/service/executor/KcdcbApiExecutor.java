package com.kcdcb.apigateway.service.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kcdcb.apigateway.Constants;
import com.kcdcb.apigateway.dto.ProxyRequest;
import com.kcdcb.apigateway.exception.error.ErrorResponse;
import com.kcdcb.apigateway.exception.error.KcdCbError;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor

public class KcdcbApiExecutor implements ApiExecutor{
    private final BasicApiExecutor basicApiExecutor;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity execute(HttpServletRequest httpServletRequest, String user, ProxyExchange<?> proxy) throws HttpRequestMethodNotSupportedException, JsonProcessingException {
        ResponseEntity<?> responseEntity1Step = basicApiExecutor.execute(httpServletRequest, user, proxy);
        ProxyRequest proxyRequest = new ProxyRequest("/v1/sample/members", HttpMethod.GET.toString(), httpServletRequest.getHeader(Constants.KCDCB_REQUEST_HEADER),httpServletRequest.getQueryString(), httpServletRequest.getParameterMap());
        ResponseEntity<?> responseEntity2Step = basicApiExecutor.execute(proxyRequest, user, proxy);

        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();

        if((HttpStatus.OK).equals(responseEntity1Step.getStatusCode()) && (HttpStatus.OK).equals(responseEntity2Step.getStatusCode())) {
            String jsonString1 = objectMapper.writeValueAsString(responseEntity1Step.getBody());
            String jsonString2 = objectMapper.writeValueAsString(responseEntity2Step.getBody());

            objectNode.set("Result1", objectMapper.readTree(jsonString1));
            objectNode.set("Result2", objectMapper.readTree(jsonString2));
            return new ResponseEntity<>(objectNode, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse(KcdCbError.HTTP_PROXY_EXCEPTION), HttpStatus.BAD_GATEWAY);
        }
    }
}
