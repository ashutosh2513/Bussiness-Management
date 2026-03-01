package com.smartbilling.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class InvoiceMailService {
    private final JavaMailSender mailSender;
    private final String fromAddress;

    public InvoiceMailService(
            JavaMailSender mailSender,
            @Value("${spring.mail.username:no-reply@smartbilling.local}") String fromAddress
    ) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    public void sendInvoice(String toEmail, String subject, String body, byte[] pdfBytes, String fileName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(fileName, new ByteArrayResource(pdfBytes));
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new IllegalArgumentException("Unable to send invoice mail");
        }
    }
}
