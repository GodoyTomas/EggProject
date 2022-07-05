package com.esoa.demo.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender sender;

    @Value("endangered.species.of.animals@gmail.com")
    private String from;

    private static final String[] SUBJECT = {"Mail de bienvenida",
    "Gracias por tu contacto"};
    private static final String[] TEXT = {"Bienvenidos. \n Muchas gracias por registrarte!",
    "Agradecemos tus comentario. \n Pronto recibiras noticias!"};
    
    @Async
    public void send(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(SUBJECT[0]);
        message.setText(TEXT[0]);
        sender.send(message);
    }

    @Async
    public void sendGreateful(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(SUBJECT[1]);
        message.setText(TEXT[1]);
        sender.send(message);
    }

}
