package org.webonise.gitmetrics.Controllers.Webhooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.webonise.gitmetrics.Services.JsonParser;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestCommentService;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestReviewCommentService;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestReviewService;
import org.webonise.gitmetrics.Services.Webhooks.PullRequestService;

@RestController
@RequestMapping("webhooks/pullrequest")
public class PullRequestController {

    @Autowired
    JsonParser jsonParser;

    @Autowired
    PullRequestCommentService pullRequestCommentService;

    @Autowired
    PullRequestService pullRequestService;

    @Autowired
    PullRequestReviewService pullRequestReviewService;

    @Autowired
    PullRequestReviewCommentService pullRequestReviewCommentService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String pullRequestPayload(@RequestBody String payload) {
        pullRequestService.actionOn(payload);
        return "ok Pull Request Webhook Controller";
    }

    @RequestMapping(value = "/review", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String pullRequestReviewPayload(@RequestBody String payload) {
        pullRequestReviewService.actionOn(payload);
        return "ok Pull Request Review Webhook Controller";
    }

    @RequestMapping(value = "/review/comment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String pullRequestReviewCommentPayload(@RequestBody String payload) {
        pullRequestReviewCommentService.actionOn(payload);
        return "ok Pull Request Review Comment Webhook Controller";
    }

    @RequestMapping(value = "/issue/comment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String pullRequestCommentPayload(@RequestBody String payload) {
        pullRequestCommentService.actionOn(payload);
        return "ok Pull Request Comment Webhook Controller";
    }
}
