package com.atamertc.service;

import com.atamertc.rabbitmq.model.MailSendModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendMail(MailSendModel model) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${sma_mail}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Social Media App Yeni Uyelik Mail Aktivasyonu");
        mailMessage.setText("Aktivasyon kodunuz: " + model.getActivationCode());
        javaMailSender.send(mailMessage);
    }


}
