package com.algafood.algafoodapi.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .cors()
                .and()
                // .oauth2ResourceServer().opaqueToken();
                .oauth2ResourceServer().jwt();
    }

    // JWT COM CHAVE SIMÉTRICA
    // @Bean
    // public JwtDecoder jwtDecoder() {
    // var secretKey = new
    // SecretKeySpec("63dcb114-4869-4e1d-bbc0-60028323f6e2".getBytes(),
    // "HmacSHA256");

    // return NimbusJwtDecoder.withSecretKey(secretKey).build();
    // }
}
