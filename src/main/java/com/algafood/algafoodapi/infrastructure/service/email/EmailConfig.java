package com.algafood.algafoodapi.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algafood.algafoodapi.domain.service.EnvioEmailService;

@Configuration
public class EmailConfig {
    
    @Value("${algafood.email.impl}")
    private ImplType type;

    public enum ImplType {
        SMTP, FAKE
    }

    @Bean
    public EnvioEmailService envioEmailService() {
        if(ImplType.FAKE.equals(type)) {
            return new FakeEnvioEmailService();
        } else {
            return new SmtpEnvioEmailService();
        }
    }

}
