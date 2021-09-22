package com.kcdcb.apigateway.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "app")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
@Validated
public class AppProperties {
    @NotBlank
    private final String defaultProxyHost;
}
