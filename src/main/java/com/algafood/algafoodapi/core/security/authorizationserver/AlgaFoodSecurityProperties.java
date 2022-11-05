package com.algafood.algafoodapi.core.security.authorizationserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
@Validated
@ConfigurationProperties("algafood.auth")
public class AlgaFoodSecurityProperties {

    private String providerUrl;
}
