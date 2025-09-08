package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendLoginNotification(String toEmail, String ipAddress) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            // Gmail username එකට සමාන From email එක
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
            log.info("✅ Login notification email sent to {}", toEmail);

        } catch (Exception e) {
            log.error("❌ Failed to send login notification to {}: {}", toEmail, e.getMessage(), e);
        }
    }
}
