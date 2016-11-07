package org.webonise.gitmetrics.services.implementations.webhooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.documents.Comment;
import org.webonise.gitmetrics.services.interfaces.DatabaseService;
import org.webonise.gitmetrics.services.interfaces.JsonParser;
import org.webonise.gitmetrics.services.interfaces.webhooks.PullRequestReviewCommentService;
import org.webonise.gitmetrics.services.interfaces.webhooks.PullRequestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PullRequestReviewCommentServiceImpl implements PullRequestReviewCommentService {
    private static final Logger logger = Logger.getLogger(PullRequestService.class);
    private static final String ACTION_KEY = "action";
    private static final String CREATE_KEY = "created";
    private static final String EDIT_KEY = "edited";
    private static final String DELETE_KEY = "deleted";

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private Gson gson;

    @Autowired
    private DatabaseService databaseService;

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
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));
        String comment = jsonParser.parse(payload, "comment");
        long reviewId = Long.parseLong(jsonParser.parse(comment, "pull_request_review_id"));

        List<String> keyList = new ArrayList();
        keyList.add("id");
        keyList.add("body");
        keyList.add("user.login");

        comment = jsonParser.parse(comment, keyList);

        String sender = jsonParser.parse(payload, "sender.login");
        String createdAt = jsonParser.parse(payload, "comment.created_at");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");

        comment = jsonParser.addToJson(comment, "createdAt", createdAt);
        comment = jsonParser.addToJson(comment, "updatedAt", updatedAt);

        Map<String, Object> keys = new HashMap();
        keys.put("sender", sender);
        keys.put("updatedAt", updatedAt);

        Comment newComment = gson.fromJson(comment, Comment.class);
        databaseService.addReviewCommentToReview(repositoryName, number, reviewId, newComment, keys);
    }

    private void actionOnEdit(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));
        String comment = jsonParser.parse(payload, "comment");
        long reviewId = Long.parseLong(jsonParser.parse(comment, "pull_request_review_id"));

        List<String> keyList = new ArrayList();
        keyList.add("id");
        keyList.add("body");
        keyList.add("user.login");

        comment = jsonParser.parse(comment, keyList);

        String sender = jsonParser.parse(payload, "sender.login");
        String createdAt = jsonParser.parse(payload, "comment.created_at");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");

        comment = jsonParser.addToJson(comment, "createdAt", createdAt);
        comment = jsonParser.addToJson(comment, "updatedAt", updatedAt);

        Map<String, Object> keys = new HashMap();
        keys.put("sender", sender);
        keys.put("updatedAt", updatedAt);

        Comment updatedComment = gson.fromJson(comment, Comment.class);
        databaseService.editReviewCommentOnReview(repositoryName, number, reviewId, updatedComment, keys);
    }

    private void actionOnDelete(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "pull_request.number"));
        long reviewId = Long.parseLong(jsonParser.parse(payload, "comment.pull_request_review_id"));
        long commentId = Long.parseLong(jsonParser.parse(payload, "comment.id"));

        String sender = jsonParser.parse(payload, "sender.login");
        String updatedAt = jsonParser.parse(payload, "pull_request.updated_at");

        Map<String, Object> keys = new HashMap();
        keys.put("sender", sender);
        keys.put("updatedAt", updatedAt);

        databaseService.deleteReviewCommentFromReview(repositoryName, number, reviewId, commentId, keys);
    }
}

