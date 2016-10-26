package org.webonise.gitmetrics.ServiceImplementations.Webhooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Documents.Comment;
import org.webonise.gitmetrics.Services.DatabaseService;
import org.webonise.gitmetrics.Services.JsonParser;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestCommentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PullRequestCommentServiceImpl implements PullRequestCommentService {
    private static final Logger logger = Logger.getLogger(PullRequestCommentService.class);
    private static final String ACTION_KEY = "action";
    private static final String CREATE_VALUE = "created";
    private static final String EDIT_VALUE = "edited";
    private static final String DELETE_VALUE = "deleted";

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
            case CREATE_VALUE:
                actionOnCreate(payload);
                break;
            case EDIT_VALUE:
                actionOnEdit(payload);
                break;
            case DELETE_VALUE:
                actionOnDelete(payload);
                break;
            default:
                logger.trace("No Valid Action Found In Payload:\n" + payload);
        }
    }

    private void actionOnCreate(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "issue.number"));
        String comment = jsonParser.parse(payload, "comment");

        List<String> keyList = new ArrayList();
        keyList.add("id");
        keyList.add("body");
        keyList.add("user.login");

        comment = jsonParser.parse(comment, keyList);
        String updatedAt = jsonParser.parse(payload, "comment.updated_at");
        String createdAt = jsonParser.parse(payload, "comment.created_at");
        comment = jsonParser.addToJson(comment, "updatedAt", updatedAt);
        comment = jsonParser.addToJson(comment, "createdAt", createdAt);

        String sender = jsonParser.parse(payload, "sender.login");

        Map<String, Object> keys = new HashMap();
        keys.put("sender", sender);
        keys.put("updatedAt", updatedAt);

        Comment newComment = gson.fromJson(comment, Comment.class);
        databaseService.addCommentOnPullRequest(repositoryName, number, newComment, keys);
    }

    private void actionOnDelete(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "issue.number"));
        long commentId = Long.parseLong(jsonParser.parse(payload, "comment.id"));

        String sender = jsonParser.parse(payload, "sender.login");
        String updatedAt = jsonParser.parse(payload, "comment.updated_at");

        Map<String, Object> keys = new HashMap();
        keys.put("sender", sender);
        keys.put("updatedAt", updatedAt);

        databaseService.removeCommentFromPullRequest(repositoryName, number, commentId, keys);
    }

    private void actionOnEdit(String payload) {
        String repositoryName = jsonParser.parse(payload, "repository.name");
        int number = Integer.parseInt(jsonParser.parse(payload, "issue.number"));
        long commentId = Long.parseLong(jsonParser.parse(payload, "comment.id"));
        String updatedComment = jsonParser.parse(payload, "comment.body");
        String sender = jsonParser.parse(payload, "sender.login");
        String updatedAt = jsonParser.parse(payload, "comment.updated_at");

        Map<String, Object> keys = new HashMap();
        keys.put("sender", sender);
        keys.put("updatedAt", updatedAt);

        databaseService.editCommentOnPullRequest(repositoryName, number, commentId, updatedComment, keys);
    }
}

