package org.webonise.gitmetrics.services.interfaces;

public interface EmailService {
    void send(String toEmail, String body, String subject);
}