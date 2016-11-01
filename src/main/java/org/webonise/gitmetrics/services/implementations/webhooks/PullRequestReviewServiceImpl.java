package org.webonise.gitmetrics.services.implementations.webhooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.documents.Review;
import org.webonise.gitmetrics.services.interfaces.DatabaseService;
import org.webonise.gitmetrics.services.interfaces.JsonParser;
import org.webonise.gitmetrics.services.interfaces.webhooks.PullRequestReviewService;
import org.webonise.gitmetrics.services.interfaces.webhooks.PullRequestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PullRequestReviewServiceImpl implements PullRequestReviewService {
    private static final Logger logger = Logger.getLogger(PullRequestService.class);
    private static final String ACTION_KEY = "action";
    private static final String SUBMIT_VALUE = "submitted";

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private Gson gson;

    @Autowired
    private DatabaseService databaseService;

    @Override
    public void actionOn(String payload) {
        String action = gson.fromJson(payload, JsonObject.class).get(ACTION_KEY).getAsString();

        switch (action) {
            case SUBMIT_VALUE:
                actionOnSubmit(payload);
                break;
            default:
                logger.error("No Valid Action Found In Payload:\n" + payload);
        }
    }

    private void actionOnSubmit(String payload) {
        System.out.println("new review request");
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));

        String review = jsonParser.parse(payload, "review");
        String submittedAt = jsonParser.parse(review, "submitted_at");

        List<String> keyList = new ArrayList();
        keyList.add("id");
        keyList.add("body");
        keyList.add("user.login");

        review = jsonParser.parse(review, keyList);
        review = jsonParser.addToJson(review, "submittedAt", submittedAt);
        review = jsonParser.addToJson(review, "comments", new JSONArray());

        String sender = jsonParser.parse(payload, "sender.login");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");

        Map<String, Object> keys = new HashMap();
        keys.put("sender", sender);
        keys.put("updatedAt", updatedAt);

        Review newReview = gson.fromJson(review, Review.class);
        databaseService.addReviewToPullRequest(repositoryName, number, newReview, keys);
    }
}

