package org.webonise.gitmetrics.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Services.DatabaseService;
import org.webonise.gitmetrics.Services.EmailService;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class StaleBranchNotificationMailer {
    @Autowired
    private StaleBranchDetector staleBranchDetector;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private EmailService emailService;


    @Scheduled(cron = "*/15 * * * * *")
    public void task() {
        Set<StaleBranch> staleBranches;
        List<RepositoryDetails> repositoryDetailsList;
        try {
            repositoryDetailsList = databaseService.getRepositoryDetails();
            for (int i = 0; i < repositoryDetailsList.size(); i++) {
                RepositoryDetails repositoryDetails = repositoryDetailsList.get(i);
                staleBranchDetector.fetchData(repositoryDetails.getOwner(), repositoryDetails.getName());
                staleBranches = staleBranchDetector.getMailingList();
                Iterator<StaleBranch> iterator = staleBranches.iterator();
                while (iterator.hasNext()) {
                    StaleBranch staleBranch = iterator.next();
                    emailService.send(staleBranch.getEmail(), staleBranch.getName());
                    databaseService.updateMailSentValue(repositoryDetails.getName(), staleBranch);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
