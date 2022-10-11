package com.algafood.algafoodapi.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

public class SandBoxEnvioEmailService extends SmtpEnvioEmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    @Value("${algafood.email.remetente}")
    private String remetente;

    @Value("${spring.email.destinatario}")
    private String destinatario;

    public void enviar(Mensagem mensagem) {
        try {
            MimeMessage mimeMessage = super.criarMimeMessage(mensagem);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-mail.", e);
        }
    }

}
