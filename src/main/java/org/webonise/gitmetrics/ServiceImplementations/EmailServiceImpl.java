package org.webonise.gitmetrics.ServiceImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.webonise.gitmetrics.Services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private MailSender mailSender;

    private SimpleMailMessage simpleMailMessage;

    @Value("${send.from.email}")
    private String fromEmail;

    @Override
    public void send(String toEmail, String data) {
        this.simpleMailMessage = new SimpleMailMessage();

        this.simpleMailMessage.setSubject("Stale Branches");
        this.simpleMailMessage.setFrom(this.fromEmail);
        this.simpleMailMessage.setTo(toEmail);

        simpleMailMessage.setText("You have "+ data +" as a stale branch");

        try {
            this.mailSender.send(simpleMailMessage);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}