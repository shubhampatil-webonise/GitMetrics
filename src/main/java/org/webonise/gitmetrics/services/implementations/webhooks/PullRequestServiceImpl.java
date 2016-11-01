package org.webonise.gitmetrics.services.implementations.webhooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.documents.Assignee;
import org.webonise.gitmetrics.documents.Label;
import org.webonise.gitmetrics.documents.PullRequest;
import org.webonise.gitmetrics.services.interfaces.DatabaseService;
import org.webonise.gitmetrics.services.interfaces.JsonParser;
import org.webonise.gitmetrics.services.interfaces.webhooks.PullRequestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PullRequestServiceImpl implements PullRequestService {
    private static final Logger logger = Logger.getLogger(PullRequestService.class.getName());
    private static final String ACTION_KEY = "action";
    private static final String OPEN_VALUE = "opened";
    private static final String CLOSE_VALUE = "closed";
    private static final String REOPEN_VALUE = "reopened";
    private static final String EDIT_VALUE = "edited";
    private static final String ASSIGN_VALUE = "assigned";
    private static final String UNASSIGN_VALUE = "unassigned";
    private static final String LABEL_VALUE = "labeled";
    private static final String UNLABEL_VALUE = "unlabeled";

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private Gson gson;

    @Override
    public void actionOn(String payload) {
        String action = gson.fromJson(payload, JsonObject.class).get(ACTION_KEY).getAsString();

        switch (action) {
            case OPEN_VALUE:
                actionOnOpen(payload);
                break;
            case CLOSE_VALUE:
                actionOnClose(payload);
                break;
            case REOPEN_VALUE:
                actionOnReopen(payload);
                break;
            case EDIT_VALUE:
                actionOnEdit(payload);
                break;
            case ASSIGN_VALUE:
                actionOnAssign(payload);
                break;
            case UNASSIGN_VALUE:
                actionOnUnassign(payload);
                break;
            case LABEL_VALUE:
                actionOnLabel(payload);
                break;
            case UNLABEL_VALUE:
                actionOnUnlabel(payload);
                break;
            default:
                logger.error("No Valid Action Found In Payload:\n" + payload);
        }
    }

    private void actionOnOpen(String payload) {
        String pullRequest = jsonParser.parse(payload, "pull_request");

        List<String> keys = new ArrayList();
        keys.add("number");
        keys.add("state");
        keys.add("title");
        keys.add("user.login");
        keys.add("user.type");
        keys.add("body");
        keys.add("assignees");
        keys.add("head.ref");
        keys.add("head.user.login");
        keys.add("head.user.type");
        keys.add("head.repo.name");
        keys.add("head.repo.owner.login");
        keys.add("base.ref");
        keys.add("base.user.login");
        keys.add("base.user.type");
        keys.add("base.repo.name");
        keys.add("base.repo.owner.login");
        keys.add("merged");

        pullRequest = jsonParser.parse(pullRequest, keys);
        pullRequest = jsonParser.addToJson(pullRequest, "reviews", new JSONArray());
        pullRequest = jsonParser.addToJson(pullRequest, "labels", new JSONArray());
        pullRequest = jsonParser.addToJson(pullRequest, "comments", new JSONArray());

        String senderLogin = jsonParser.parse(payload, "sender.login");
        pullRequest = jsonParser.addToJson(pullRequest, "sender", senderLogin);

        String mergedBy = jsonParser.parse(payload, "pull_request.merged_by");
        String mergedAt = jsonParser.parse(payload, "pull_request.merged_at");
        String createdAt = jsonParser.parse(payload, "pull_request.created_at");
        String closedAt = jsonParser.parse(payload, "pull_request.closed_at");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");

        pullRequest = jsonParser.addToJson(pullRequest, "mergedBy", mergedBy);
        pullRequest = jsonParser.addToJson(pullRequest, "mergedAt", mergedAt);
        pullRequest = jsonParser.addToJson(pullRequest, "createdAt", createdAt);
        pullRequest = jsonParser.addToJson(pullRequest, "closedAt", closedAt);
        pullRequest = jsonParser.addToJson(pullRequest, "updatedAt", updatedAt);

        String repositoryName = jsonParser.parse(payload, "repository.name");

        databaseService.savePullRequestInRepository(repositoryName, gson.fromJson(pullRequest, PullRequest.class));
    }

    private void actionOnClose(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        String state = jsonParser.parse(payload, "pull_request.state");
        Boolean merged = Boolean.parseBoolean(jsonParser.parse(payload, "pull_request.merged"));
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));

        Map<String, Object> keys = new HashMap<>();
        String closedAt = jsonParser.parse(payload, "pull_request.closed_at");
        String mergedAt = jsonParser.parse(payload, "pull_request.merged_at");
        String sender = jsonParser.parse(payload, "sender.login");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");

        keys.put("closedAt", closedAt);
        keys.put("mergedAt", mergedAt);
        keys.put("sender", sender);
        keys.put("updatedAt", updatedAt);
        keys.put("state", state);

        if (merged.equals(true)) {
            String mergedBy = jsonParser.parse(payload, "pull_request.merged_by.login");
            keys.put("merged", merged);
            keys.put("mergedBy", mergedBy);
        }

        databaseService.closePullRequest(repositoryName, number, keys);
    }

    private void actionOnReopen(String payload) {
        actionOnClose(payload);
    }

    private void actionOnEdit(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));
        String changes = jsonParser.parse(payload, "changes");

        String editedKey = new JSONObject(changes).keySet().iterator().next().toString();
        String editedValue = jsonParser.parse(payload, "pull_request." + editedKey);
        String sender = jsonParser.parse(payload, "sender.login");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");

        Map<String, Object> keys = new HashMap();
        keys.put(editedKey, editedValue);
        keys.put("sender", sender);
        keys.put("updatedAt", updatedAt);

        databaseService.editPullRequest(repositoryName, number, keys);
    }

    private void actionOnAssign(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));

        List<String> keyList = new ArrayList();
        keyList.add("assignee.login");
        keyList.add("assignee.type");

        String assignee = jsonParser.parse(payload, keyList);
        String sender = jsonParser.parse(payload, "sender.login");

        assignee = jsonParser.parse(assignee, "assignee");
        assignee = jsonParser.addToJson(assignee, "sender", sender);

        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");

        Map<String, Object> keys = new HashMap();
        keys.put("updatedAt", updatedAt);
        keys.put("sender", sender);

        Assignee newAssignee = gson.fromJson(assignee, Assignee.class);
        databaseService.addAssigneeInPullRequest(repositoryName, number, newAssignee, keys);
    }

    private void actionOnUnassign(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));
        String assigneeLogin = jsonParser.parse(payload, "assignee.login");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");
        String sender = jsonParser.parse(payload, "sender.login");

        Map<String, Object> keys = new HashMap();
        keys.put("updatedAt", updatedAt);
        keys.put("sender", sender);

        databaseService.removeAssigneeFromPullRequest(repositoryName, number, assigneeLogin, keys);
    }

    private void actionOnLabel(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));

        List<String> keyList = new ArrayList();
        keyList.add("label.name");
        keyList.add("label.color");

        String label = jsonParser.parse(payload, keyList);
        String sender = jsonParser.parse(payload, "sender.login");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");
        label = jsonParser.parse(label, "label");
        label = jsonParser.addToJson(label, "sender", sender);

        Map<String, Object> keys = new HashMap();
        keys.put("updatedAt", updatedAt);
        keys.put("sender", sender);

        Label newLabel = gson.fromJson(label, Label.class);
        databaseService.addLabelToPullRequest(repositoryName, number, newLabel, keys);
    }

    private void actionOnUnlabel(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));
        String labelName = jsonParser.parse(payload, "label.name");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");
        String sender = jsonParser.parse(payload, "sender.login");

        Map<String, Object> keys = new HashMap();
        keys.put("updatedAt", updatedAt);
        keys.put("sender", sender);

        databaseService.removeLabelFromPullRequest(repositoryName, number, labelName, keys);
    }
}

