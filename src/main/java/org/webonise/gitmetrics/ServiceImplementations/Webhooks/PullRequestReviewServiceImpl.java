package org.webonise.gitmetrics.ServiceImplementations.Webhooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Services.JsonParser;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestReviewService;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestService;

import java.util.ArrayList;
import java.util.List;

@Component
public class PullRequestReviewServiceImpl implements PullRequestReviewService {
    private static final Logger logger = Logger.getLogger(PullRequestService.class.getName());
    private static final String ACTION_KEY = "action";
    private static final String SUBMIT_VALUE = "submitted";

    @Autowired
    private JsonParser jsonParser;

    @Override
    public void actionOn(String payload) {
        String action = new Gson().fromJson(payload, JsonObject.class).get(ACTION_KEY).getAsString();

        switch (action) {
            case SUBMIT_VALUE:
                actionOnSubmit(payload);
                break;
            default:
                logger.error("No Valid Action Found In Payload:\n" + payload);
        }
    }

    private void actionOnSubmit(String payload) {
        String reviewKey = "review";
        String reviewBody = jsonParser.parse(payload, reviewKey);
        List<String> keys = new ArrayList();
        keys.add("id");
        keys.add("body");
        keys.add("submitted_at");
        String requiredReviewBody = jsonParser.parse(reviewBody, keys);

        String reviewerKey = "user.login";
        String reviewerValue = jsonParser.parse(reviewBody, reviewerKey);
        String reviewer = "user";
        requiredReviewBody = jsonParser.addToJson(requiredReviewBody, reviewer, reviewerValue);

        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "pull_request.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";

        String updatedAtKey = "pull_request.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);
    }
}

