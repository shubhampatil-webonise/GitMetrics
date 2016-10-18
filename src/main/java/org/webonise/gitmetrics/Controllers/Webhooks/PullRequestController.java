package org.webonise.gitmetrics.Controllers.Webhooks;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.webonise.githubmetrics.webhooktest.Services.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("webhooks/pullrequest")
public class PullRequestController {

    @Autowired
    JsonParser jsonParser;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String pullRequestPayload(@RequestBody String payload) {
        System.out.println(payload);
        System.out.println("========================================================================================");
        return "ok Pull Request Webhook Controller";
    }

    @RequestMapping(value = "/review", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String pullRequestReviewPayload(@RequestBody String payload) {
        System.out.println(payload);
        System.out.println("========================================================================================");
        return "ok Pull Request Review Webhook Controller";
    }

    @RequestMapping(value = "/review/comment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String pullRequestReviewCommentPayload(@RequestBody String payload) {
        System.out.println(payload);
        System.out.println("========================================================================================");
        return "ok Pull Request Review Comment Webhook Controller";
    }

    @RequestMapping(value = "/issue/comment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String pullRequestCommentPayload(@RequestBody String payload) {
        System.out.println("========================================================================================");
        List<String> keys = new ArrayList();
        System.out.println(payload);
        System.out.println("========================================================================================");
        keys.add("action");
        /*keys.add("changes.body.from");*/
       /* keys.add("issue.url");
        keys.add("issue.title");
        keys.add("comment.url");
        keys.add("comment.user.login");
        keys.add("comment.created_at");
        keys.add("comment.updated_at");
        keys.add("comment.body");
        keys.add("repository.name");
        keys.add("repository.url");
        keys.add("organization.url");
        keys.add("sender.login");*/
        JSONObject payloadJsonObject = new JSONObject(payload);
        Map<String, Object> result = jsonParser.parse(payloadJsonObject, keys);

        result.forEach((s, s2) -> System.out.println(s + ":" + s2));

        return "ok Pull Request Comment Webhook Controller";
    }
}
