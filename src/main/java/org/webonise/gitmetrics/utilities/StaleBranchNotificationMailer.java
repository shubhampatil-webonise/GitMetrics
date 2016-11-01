package org.webonise.gitmetrics.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.documents.Repository;
import org.webonise.gitmetrics.entities.GitRepository;
import org.webonise.gitmetrics.services.interfaces.DatabaseService;
import org.webonise.gitmetrics.services.interfaces.EmailService;

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


    @Scheduled(cron = " 30 * * * * *")
    public void task() {
        Set<StaleBranch> staleBranches;
        List<GitRepository> repositoryList;
        try {
            repositoryList = databaseService.findListOfRepositories();
            for (int i = 0; i < repositoryList.size(); i++) {
                Repository repository = databaseService.findRepositoryDetailsByName(repositoryList.get(i).getName());
                staleBranchDetector.fetchData(repository.getOwner().getLogin(), repository.getName());
                staleBranches = staleBranchDetector.getMailingList();
                Iterator<StaleBranch> iterator = staleBranches.iterator();
                while (iterator.hasNext()) {
                    StaleBranch staleBranch = iterator.next();
                    emailService.send(staleBranch.getEmail(), staleBranch.getName());
                    databaseService.updateMailSent(repository.getName(), staleBranch.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
