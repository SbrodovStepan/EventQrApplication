package com.company.eventqrapplication.service;

import com.company.eventqrapplication.entity.EventParticipant;
import com.company.eventqrapplication.entity.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EventEmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithQr(EventParticipant participant) {
        if (participant.getUser().getEmail() == null) {
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(participant.getUser().getEmail());
            helper.setSubject("Ваша заявка на событие: " + participant.getEventRequest().getEventName());

            String text = buildEmailText(participant.getEventRequest(), participant.getUser().getFirstName());
            helper.setText(text, true);

            if (participant.getQrCode() != null) {
                helper.addAttachment(
                        "qr-code.png",
                        new ByteArrayResource(participant.getQrCode())
                );
            }

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String buildEmailText(EventRequest request, String userName) {
        return "<p>Здравствуйте, " + userName + "!</p>" +
                "<p>Ваша заявка на событие <b>" + request.getEventName() + "</b> успешно создана.</p>" +
                "<p>Дата: " + request.getEventDate() + "</p>" +
                "<p>Пожалуйста, используйте прикреплённый QR-код для участия.</p>" +
                "<p>Спасибо!</p>";
    }
}
