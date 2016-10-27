package org.webonise.gitmetrics.ServiceImplementations.Webhooks;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webonise.gitmetrics.Documents.Branch;
import org.webonise.gitmetrics.Services.DatabaseService;
import org.webonise.gitmetrics.Services.JsonParser;
import org.webonise.gitmetrics.Services.Webhooks.BranchService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private Gson gson;

    private static final String REPOSITORY = "repository.name";
    private static final String BRANCH = "ref";
    private static final String BRANCH_CREATOR = "sender.login";
    private static final String STALE_KEY = "stale";
    private static final String MAIL_SENT_KEY = "mailSent";
    private static final String SENDER = "sender";

    @Override
    public void actionOnCreate(String payload) {
        List<String> keylist = new ArrayList<String>();
        keylist.add(BRANCH);
        String branch = jsonParser.parse(payload, keylist);
        String sender = jsonParser.parse(payload, BRANCH_CREATOR);
        String repository = jsonParser.parse(payload, REPOSITORY);
        branch = jsonParser.addToJson(branch, STALE_KEY, false);
        branch = jsonParser.addToJson(branch, MAIL_SENT_KEY, false);
        branch = jsonParser.addToJson(branch, SENDER, sender);
        Branch newBranch = gson.fromJson(branch, Branch.class);
        databaseService.addBranchToRepository(repository, newBranch);
    }

    @Override
    public void actionOnDelete(String payload) {
        String branch = jsonParser.parse(payload, BRANCH);
        String repository = jsonParser.parse(payload, REPOSITORY);
        databaseService.deleteBranchFromRepository(repository, branch);
    }
}
