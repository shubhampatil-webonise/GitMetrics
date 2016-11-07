package org.webonise.gitmetrics.services.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void send(String toEmail, String body, String subject);
}