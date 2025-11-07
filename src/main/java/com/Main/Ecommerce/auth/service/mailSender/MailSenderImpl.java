package com.Main.Ecommerce.auth.service.mailSender;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor

public class MailSenderImpl {

    private final JavaMailSender mailSender;

    public void sendMail(String email, String token) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            ClassPathResource resource = new ClassPathResource("templates/OptEmail.html");
            InputStream is = resource.getInputStream();
            String a = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            a = a.replace("{{name}}",email).replace("{{otp}}",token);
            helper.setTo(email);
            helper.setSubject("رمز بکبار مصرف");
            helper.setText(a, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}
