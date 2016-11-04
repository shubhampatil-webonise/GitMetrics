package org.webonise.gitmetrics.services.implementations;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.services.interfaces.EmailService;

@Component
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);

    @Autowired
    private MailSender mailSender;

    private SimpleMailMessage simpleMailMessage;

    @Value("${send.from.email}")
    private String fromEmail;

    @Override
    public void send(String toEmail, String body,String subject) {
        this.simpleMailMessage = new SimpleMailMessage();
        this.simpleMailMessage.setSubject(subject);
        this.simpleMailMessage.setFrom(this.fromEmail);
        this.simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setText(body);

        try {
            this.mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            logger.error(e.getStackTrace());
        }
    }
}