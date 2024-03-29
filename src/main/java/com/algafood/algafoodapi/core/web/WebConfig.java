package com.algafood.algafoodapi.core.web;

import jakarta.servlet.Filter;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // @Autowired
    // private ApiDeprecationHandler apiDeprecationHandler;

    // @Autowired
    // private ApiRetirementHandler apiRetirementHandler;

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    // registry.addMapping("/**")
    // .allowedMethods("*");
    // }

    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    // registry.addInterceptor(apiRetirementHandler);
    // }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaTypesWeb.V2_APPLICATION_JSON, MediaType.APPLICATION_JSON);
    }

    @Bean
    public Filter shallowEtageHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}