package com.kcdcb.apigateway.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class QueryString {
    public static String getRequestString(Class clazz, Object o) throws IllegalAccessException {
        StringBuilder queryStringBuilder = new StringBuilder();
        final Map<String, String> queryParams = new LinkedHashMap<>();

            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                queryParams.put(f.getName(), String.valueOf(f.get(o) == null ? "" : f.get(o)));
            }
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                if(StringUtils.hasLength(entry.getValue())) {
                    log.debug("key : " + entry.getKey());
                    log.debug("value : " + entry.getValue());
                    queryStringBuilder.append(entry.getKey());
                    queryStringBuilder.append("=");
                    queryStringBuilder.append(entry.getValue());
                    queryStringBuilder.append("&");
                }
            }
        final String queryString = queryStringBuilder.toString();
        log.debug("queryString : " + queryString);
        if(!StringUtils.hasLength(queryString)) {
            return "";
        } else {
            return "?" + queryString.substring(0, queryString.length() - 1);
        }
    }
}
