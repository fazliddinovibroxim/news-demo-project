package com.example.news_project.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    @Value("${email.from}")
    private String emailFrom;
    @Value("${email.token}")
    private String emailToken;

    public boolean sendMail(String to, String message) throws MessagingException {


        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailFrom, emailToken);
                    }
                });

        try {
            Message message2 = new MimeMessage(session);
            message2.setFrom(new InternetAddress(emailFrom));
            message2.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message2.setSubject("account verification by ProTech");

            String htmlContent = """
                    <h1>news website verifikatsiya kodi</h1>
                    <h1>Kod : %s</h1>
                    """.formatted(message);
            message2.setContent(htmlContent, "text/html");

            Transport.send(message2);

            System.out.println("HTML Email sent successfully!");
            return true;
//            throw new RuntimeException("Nimadur xato ketdi.Iltimos internetni tekshiring");
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}