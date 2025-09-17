package org.example.longdistancebusbackend.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendLoginNotification(String toEmail, String ipAddress) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("ireshchathuranga63309@gmail.com");
            message.setTo(toEmail);
            message.setSubject("Login Notification - Bus Ticketing System");

            String formattedDateTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            String text = String.format(
                    "Hello,\n\nWe detected a login to your account on %s.\nIP: %s\n\n" +
                            "If this was you, no action is required.\n" +
                            "If you did not login, please change your password immediately.",
                    formattedDateTime, ipAddress == null ? "Unknown" : ipAddress
            );

            message.setText(text);
            mailSender.send(message);
            log.info("Login notification email sent to {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send login notification to {}: {}", toEmail, e.getMessage(), e);
        }
    }

    /**
     *  2. Send E-Ticket with Embedded QR Code (Base64 image)
     */
    public void sendETicketWithQR(String toEmail, String qrCodeBase64, String subject, String contentText) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom("ireshchathuranga63309@gmail.com");

            log.info("QR Code base64 length: {}", qrCodeBase64.length());

            if (qrCodeBase64.startsWith("data:image")) {
                qrCodeBase64 = qrCodeBase64.substring(qrCodeBase64.indexOf(",") + 1);
            }

            byte[] imageBytes = Base64.getDecoder().decode(qrCodeBase64);
            log.info("Decoded image byte length: {}", imageBytes.length);

            helper.addInline("<qrCodeImage>", new ByteArrayResource(imageBytes), "image/png");

            String html = """
            <p>Dear Customer,</p>
            <p>Here is your e-ticket QR code. Please show this when boarding.</p>
            <img src="cid:qrCodeImage" alt="QR Code" style="width:150px;height:150px;"/>
            <p>Thank you for using our service!</p>
        """;

            helper.setText(html, true);
            mailSender.send(message);

            log.info("E-ticket email sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send ticket email to {}: {}", toEmail, e.getMessage(), e);
        }
    }

}
