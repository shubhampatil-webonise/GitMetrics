package org.webonise.gitmetrics.utilities;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.documents.Branch;
import org.webonise.gitmetrics.documents.PullRequest;
import org.webonise.gitmetrics.documents.Repository;
import org.webonise.gitmetrics.entities.GitRepository;
import org.webonise.gitmetrics.services.interfaces.DatabaseService;
import org.webonise.gitmetrics.services.interfaces.EmailService;
import org.webonise.gitmetrics.services.interfaces.HttpRequestResponseService;
import org.webonise.gitmetrics.services.interfaces.JsonParser;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class StaleBranchNotifier {
    private static final Logger logger = Logger.getLogger(StaleBranchNotifier.class);
    private static final long STALE_TIME_IN_DAYS = 0;

    @Value("${gitmetrics.client.personal-access-token}")
    private String TOKEN;

    @Value("${gitmetrics.org.name}")
    private String ORGANIZATION;
    @Autowired
    private HttpRequestResponseService httpRequestResponseService;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JsonParser jsonParser;

    @Scheduled(cron = " 30 * * * * *")
    public void notifyStaleBranches() throws IOException {

        List<GitRepository> repositoryList = databaseService.findListOfRepositories();
        for (int i = 0; i < repositoryList.size(); i++) {
            Repository repository = databaseService.findRepositoryDetailsByName(repositoryList.get(i).getName());

            List<PullRequest> pullRequests = repository.getPullRequests();
            List<String> branches = getMergedBranches(pullRequests);

            List<Branch> branchList = repository.getBranches();
            branches = filterBranchesBySentMailStatus(branchList, branches);

            for (String branch : branches) {
                String branchJson = getBranchJson(branch, repository.getName());
                String lastCommitDate = jsonParser.parse(branchJson, "commit.commit.committer.date");
                if (getDateDifferenceInDays(lastCommitDate) >= STALE_TIME_IN_DAYS) {
                    String email = jsonParser.parse(branchJson, "commit.commit.author.email");
                    String body = "This " + branch + "is Stale Branch In " + repository.getName();
                    emailService.send(email, body, "Stale Branch");
                    databaseService.updateMailSent(repository.getName(), branch);
                    databaseService.updateStale(repository.getName(), branch);
                }
            }
        }
    }

    private String getBranchJson(String branch, String repositoryName) throws IOException {
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", "token " + TOKEN);


        String url = "https://api.github.com/repos/" + ORGANIZATION + "/" + repositoryName + "/branches/" + branch;
        String branchJson = httpRequestResponseService.get(url, headers);

        return branchJson;
    }

    private long getDateDifferenceInDays(String date) {
        Calendar lastCommmitDate = DatatypeConverter.parseDateTime(date);
        Date currentDate = new Date();
        long timeDifference = (currentDate.getTime() - lastCommmitDate.getTime().getTime());
        long days = timeDifference / (24 * 60 * 60 * 1000);

        return days;
    }

    private List<String> filterBranchesBySentMailStatus(List<Branch> branchList, List<String> branches) {

        Iterator branchIterator = branchList.iterator();
        while (branchIterator.hasNext()) {
            Branch branch = (Branch) branchIterator.next();

            Boolean mailSent = branch.getMailSent();
            if (mailSent && branches.contains(branch.getRef())) {
                branches.remove(branch.getRef());
            }
        }

        return branches;
    }

    private List<String> getMergedBranches(List<PullRequest> pullRequests) {
        List<String> branches = new ArrayList();

        Iterator pullRequestIterator = pullRequests.iterator();
        while (pullRequestIterator.hasNext()) {
            PullRequest pullRequest = (PullRequest) pullRequestIterator.next();
            String branchName = pullRequest.getHead().getRef();

            if (branches.contains(branchName)) {
                branches.remove(branchName);
            }

            Boolean merged = pullRequest.getMerged();
            String state = pullRequest.getState();
            if (state.equals("closed") && merged) {
                branches.add(branchName);
            }
        }

        return branches;
    }
}
