package org.webonise.gitmetrics.ServiceImplementations.Webhooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Services.JsonParser;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestReviewCommentService;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestService;

import java.util.ArrayList;
import java.util.List;

@Component
public class PullRequestReviewCommentServiceImpl implements PullRequestReviewCommentService {
    private static final Logger logger = Logger.getLogger(PullRequestService.class.getName());
    private static final String ACTION_KEY = "action";
    private static final String CREATE_KEY = "created";
    private static final String EDIT_KEY = "edited";
    private static final String DELETE_KEY = "deleted";

    @Autowired
    JsonParser jsonParser;

    @Override
    public void actionOn(String payload) {
        String action = new Gson().fromJson(payload, JsonObject.class).get(ACTION_KEY).getAsString();

        switch (action) {
            case CREATE_KEY:
                actionOnCreate(payload);
                break;
            case EDIT_KEY:
                actionOnEdit(payload);
                break;
            case DELETE_KEY:
                actionOnDelete(payload);
                break;
            default:
                logger.error("No Valid Action Found In Payload:\n" + payload);
        }
    }

    private void actionOnCreate(String payload) {
        String reviewCommentKey = "comment";
        String reviewCommentBody = jsonParser.parse(payload, reviewCommentKey);

        List<String> keys = new ArrayList();
        keys.add("id");
        keys.add("body");
        keys.add("created_at");
        keys.add("updated_at");

        String requiredReviewCommentBody = jsonParser.parse(reviewCommentBody, keys);

        String reviewUserKey = "user.login";
        String reviewUserValue = jsonParser.parse(reviewCommentBody, reviewUserKey);
        String reviewUser = "user";
        requiredReviewCommentBody = jsonParser.addToJson(requiredReviewCommentBody, reviewUser, reviewUserValue);

        String reviewIdKey = "pull_request_review_id";
        int reviewId = Integer.parseInt(jsonParser.parse(reviewCommentBody, reviewIdKey));

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";

        String updatedAtKey = "pull_request.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);

        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);
    }

    private void actionOnEdit(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String changesKey = "changes";
        String changesBody = jsonParser.parse(payload, changesKey);
        String editedKey = "comment." + new JSONObject(changesBody).keySet().iterator().next().toString();

        String editedValue = jsonParser.parse(payload, editedKey);

        String reviewIdKey = "comment.pull_request_review_id";
        int reviewId = Integer.parseInt(jsonParser.parse(payload, reviewIdKey));

        String commentIdKey = "comment.id";
        int commentId = Integer.parseInt(jsonParser.parse(payload, commentIdKey));

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";

        String updatedAtKey = "pull_request.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);
    }

    private void actionOnDelete(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String reviewIdKey = "comment.pull_request_review_id";
        int reviewId = Integer.parseInt(jsonParser.parse(payload, reviewIdKey));

        String commentIdKey = "comment.id";
        int commentId = Integer.parseInt(jsonParser.parse(payload, commentIdKey));
    }
}

