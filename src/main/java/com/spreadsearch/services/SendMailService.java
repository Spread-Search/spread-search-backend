package com.spreadsearch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailService {
    private final MailSender mailSender;
    @Value("${mail.username}")
    private String emailFrom;

    public void sendEmail(String title, String text, String emailTo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(title);
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }
}
