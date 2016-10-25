package org.webonise.gitmetrics.Services;

public interface EmailService {
    void send(String toEmail,String staleBranchName);
}