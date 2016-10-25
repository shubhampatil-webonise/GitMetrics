package org.webonise.gitmetrics.ServiceImplementations.Webhooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Services.JsonParser;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestCommentService;

import java.util.ArrayList;
import java.util.List;

@Component
public class PullRequestCommentServiceImpl implements PullRequestCommentService {
    private static final Logger logger = Logger.getLogger(PullRequestCommentService.class);
    private static final String ACTION_KEY = "action";
    private static final String CREATE_VALUE = "created";
    private static final String EDIT_VALUE = "edited";
    private static final String DELETE_VALUE = "deleted";

    @Autowired
    JsonParser jsonParser;

    @Override
    public void actionOn(String payload) {
        String action = new Gson().fromJson(payload, JsonObject.class).get(ACTION_KEY).getAsString();

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
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "issue.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String commentKey = "comment";
        String commentBody = jsonParser.parse(payload, commentKey);

        List<String> keys = new ArrayList();
        keys.add("id");
        keys.add("body");
        keys.add("created_at");
        keys.add("updated_at");

        String requiredCommentBody = jsonParser.parse(commentBody, keys);

        String userKey = "user.login";
        String userValue = jsonParser.parse(commentBody, userKey);
        String user = "user";
        requiredCommentBody = jsonParser.addToJson(requiredCommentBody, user, userValue);

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";

        String updatedAtKey = "comment.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);
    }

    private void actionOnDelete(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "issue.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String commentIdKey = "comment.id";
        int commentIdValue = Integer.parseInt(jsonParser.parse(payload, commentIdKey));
    }

    private void actionOnEdit(String payload) {
        String repositoryNameKey = "repository.name";
        String repositoryName = jsonParser.parse(payload, repositoryNameKey);

        String pullRequestNumberKey = "issue.number";
        int number = Integer.parseInt(jsonParser.parse(payload, pullRequestNumberKey));

        String changesKey = "changes";
        String changesBody = jsonParser.parse(payload, changesKey);
        String editedKey = "comment." + new JSONObject(changesBody).keySet().iterator().next().toString();

        String editedValue = jsonParser.parse(payload, editedKey);

        String senderKey = "sender.login";
        String senderValue = jsonParser.parse(payload, senderKey);
        String requiredSenderKey = "sender";

        String updatedAtKey = "comment.updated_at";
        String updatedAt = jsonParser.parse(payload, updatedAtKey);
    }
}

