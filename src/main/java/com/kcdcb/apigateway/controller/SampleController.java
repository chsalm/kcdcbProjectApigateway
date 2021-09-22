package com.kcdcb.apigateway.controller;

import com.kcdcb.apigateway.service.ApiGatewayService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/v1/{user}/sample/**")
@Slf4j
@RestController
@RequiredArgsConstructor
public class SampleController {

    private final ApiGatewayService apiGatewayService;

    @ApiOperation(value = "Proxy API", notes = "Proxy API")
    @RequestMapping
    public ResponseEntity<?> getProxy(ProxyExchange<?> proxy, @PathVariable("user") String user, HttpServletRequest httpServletRequest) throws Exception {
        log.debug("user: " + user);
        return apiGatewayService.doProxy(httpServletRequest, user, proxy);
    }
    
}
