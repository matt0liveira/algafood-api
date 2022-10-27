package com.algafood.algafoodapi.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class HalConfigurationWeb {

    @Bean
    public HalConfiguration globalPolicy() {
        return new HalConfiguration()
                .withMediaType(MediaType.APPLICATION_JSON)
                .withMediaType(MediaTypesWeb.V1_APPLICATION_JSON)
                .withMediaType(MediaTypesWeb.V2_APPLICATION_JSON);
    }

}