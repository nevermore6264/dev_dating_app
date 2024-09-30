package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.services.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
     private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {         this.mailSender = mailSender;    
}

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Hoặc xử lý lỗi theo cách khác
        }
    }

}
