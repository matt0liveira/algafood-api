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
        SMTP, FAKE, SANDBOX
    }

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (type) {
            case SMTP:
                return new SmtpEnvioEmailService();
            
            case FAKE:
                return new FakeEnvioEmailService();
                
            case SANDBOX:
                return new SandBoxEnvioEmailService();
            default:
                return null;
        }
    }

}
