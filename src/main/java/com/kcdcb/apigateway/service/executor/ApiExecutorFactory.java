package com.kcdcb.apigateway.service.executor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ApiExecutorFactory {
    private final Map<String, ApiExecutor> apiExecutorMap;
    private static final Set<String> USE_BASIC_USER = new HashSet<>(
            Arrays.asList("nice")
    );
    private static final String BASIC_API_EXECUTOR_NAME = "basicApiExecutor";
    private static final String SUFFIX_API_EXECUTOR_NAME = "ApiExecutor";

    public ApiExecutor getExecutor(HttpServletRequest httpServletRequest, String user){
        if(USE_BASIC_USER.contains(user)) {
            return apiExecutorMap.get(BASIC_API_EXECUTOR_NAME);
        } else {
            return apiExecutorMap.get(user + SUFFIX_API_EXECUTOR_NAME);
        }
    }
}
