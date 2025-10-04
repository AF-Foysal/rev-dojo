package dev.affoysal.backend.Service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import dev.affoysal.backend.Exception.ApiException;
import dev.affoysal.backend.Service.EmailService;
import dev.affoysal.backend.Utility.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String NEW_ACCOUNT_VERIFICATION = "New Account Verification";
    private static final String RESET_PASSWORD_REQUEST = "Reset Password Request";
    private final JavaMailSender mailSender;

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${EMAIL_ID}")
    private String fromEmailString;

    @Override
    @Async
    public void sendNewAccountEmail(String name, String email, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmailString);
            message.setTo(email);
            message.setText(EmailUtils.getEmailMessage(name, host, token));
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new ApiException("Failed to send email");
        }
    }

    @Override
    @Async
    public void sendResetPasswordEmail(String name, String email, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(RESET_PASSWORD_REQUEST);
            message.setFrom(fromEmailString);
            message.setTo(email);
            message.setText(EmailUtils.getResetPasswordMessage(name, host, token));
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new ApiException("Failed to send email");
        }
    }
}
