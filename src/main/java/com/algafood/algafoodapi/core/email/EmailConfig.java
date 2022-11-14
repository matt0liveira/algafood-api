package com.algafood.algafoodapi.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algafood.algafoodapi.domain.service.EnvioEmailService;
import com.algafood.algafoodapi.infrastructure.service.email.FakeEnvioEmailService;
import com.algafood.algafoodapi.infrastructure.service.email.SandBoxEnvioEmailService;
import com.algafood.algafoodapi.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    public enum ImplType {
        SMTP, FAKE, SANDBOX
    }

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
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
