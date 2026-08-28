package com.example.paqueteria.infrastructure.adapter.out.mail;

import com.example.paqueteria.application.port.out.notifications.EmailPort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailAdapter implements EmailPort {

    private final JavaMailSender mailSender;

    public EmailAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void send(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        mailSender.send(mensaje);
    }
}
