package org.webonise.gitmetrics.ServiceImplementations.Webhooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Services.JsonParser;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestService;

import java.util.ArrayList;
import java.util.List;

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
    private static final String PULL_REQUEST_KEY = "pull_request";

    @Autowired
    private JsonParser jsonParser;

    @Override
    public void actionOn(String payload) {
        String action = new Gson().fromJson(payload, JsonObject.class).get(ACTION_KEY).getAsString();

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
        String pullRequest = jsonParser.parse(payload, PULL_REQUEST_KEY);
        List<String> keys = new ArrayList();
        keys.add("number");
        keys.add("state");
        keys.add("title");
        keys.add("user.login");
        keys.add("user.type");
        keys.add("body");
        keys.add("created_at");
        keys.add("closed_at");
        keys.add("updated_at");
        keys.add("assignees");
        keys.add("head.label");
        keys.add("head.ref");
        keys.add("head.user.login");
        keys.add("head.user.type");
        keys.add("head.repo.name");
        keys.add("head.repo.owner.login");
        keys.add("base.label");
        keys.add("base.ref");
        keys.add("base.user.login");
        keys.add("base.user.type");
        keys.add("base.repo.name");
        keys.add("base.repo.owner.login");
        keys.add("merged");
        keys.add("merged_by");
        keys.add("merged_at");
        String requiredPullRequestBody = jsonParser.parse(pullRequest, keys);

        JSONArray jsonArray = new JSONArray();
        String reviewsKey = "reviews";
        requiredPullRequestBody = jsonParser.addToJson(requiredPullRequestBody, reviewsKey, jsonArray);

        String labelsKey = "labels";
        requiredPullRequestBody = jsonParser.addToJson(requiredPullRequestBody, labelsKey, jsonArray);

        String commentsKey = "comments";
        requiredPullRequestBody = jsonParser.addToJson(requiredPullRequestBody, commentsKey, jsonArray);
        
        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";
        requiredPullRequestBody = jsonParser.addToJson(requiredPullRequestBody, requiredSenderKey, senderValue);

        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);
    }

    private void actionOnClose(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String stateKey = "pull_request.state";
        String stateValue = jsonParser.parse(payload, stateKey);

        String closedAtKey = "pull_request.closed_at";
        String closedAt = jsonParser.parse(payload, closedAtKey);

        String mergedAtKey = "pull_request.merged_at";
        String mergedAt = jsonParser.parse(payload, mergedAtKey);

        String mergedKey = "pull_request.merged";
        String merged = jsonParser.parse(payload, mergedKey);
        Boolean mergedValue = Boolean.parseBoolean(merged);
        String mergedByKey = "pull_request.merged_by.login";
        String mergedBy = null;
        if (mergedValue) {
            mergedBy = jsonParser.parse(payload, mergedByKey);
        }

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";

        String updatedAtKey = "pull_request.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);
    }

    private void actionOnReopen(String payload) {
        actionOnClose(payload);
    }

    private void actionOnEdit(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String changesKey = "changes";
        String changesBody = jsonParser.parse(payload, changesKey);
        String editedKey = "pull_request." + new JSONObject(changesBody).keySet().iterator().next().toString();

        String editedValue = jsonParser.parse(payload, editedKey);

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";

        String updatedAtKey = "pull_request.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);
    }

    private void actionOnAssign(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        List<String> keys = new ArrayList();
        keys.add("assignee.login");
        keys.add("assignee.type");

        String assignee = jsonParser.parse(payload, keys);
        String assigneeKey = "assignee";
        String assigneeValue = jsonParser.parse(assignee, assigneeKey);

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";
        assigneeValue = jsonParser.addToJson(assigneeValue, requiredSenderKey, senderValue);

        String updatedAtKey = "pull_request.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);
    }

    private void actionOnUnassign(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String assgineeLoginKey = "assignee.login";
        String assigneeLogin = jsonParser.parse(payload, assgineeLoginKey);

        String updatedAtKey = "pull_request.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";
    }

    private void actionOnLabel(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        List<String> keys = new ArrayList();
        keys.add("label.name");
        keys.add("label.color");

        String labelJson = jsonParser.parse(payload, keys);
        String labelKey = "label";
        String labelValue = jsonParser.parse(labelJson, labelKey);

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";
        labelValue = jsonParser.addToJson(labelValue, requiredSenderKey, senderValue);

        String updatedAtKey = "pull_request.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);
    }

    private void actionOnUnlabel(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String labelName = jsonParser.parse(payload, "label.name");

        String updatedAtKey = "pull_request.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";
    }
}

