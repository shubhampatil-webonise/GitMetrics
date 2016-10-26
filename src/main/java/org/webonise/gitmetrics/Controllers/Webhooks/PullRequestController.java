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
@RequestMapping("/webhooks/pullrequest")
public class PullRequestController {

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private PullRequestCommentService pullRequestCommentService;

    @Autowired
    private PullRequestService pullRequestService;

    @Autowired
    private PullRequestReviewService pullRequestReviewService;

    @Autowired
    private PullRequestReviewCommentService pullRequestReviewCommentService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    void pullRequestPayload(@RequestBody String payload) {
        pullRequestService.actionOn(payload);
    }

    @RequestMapping(value = "/review", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    void pullRequestReviewPayload(@RequestBody String payload) {
        pullRequestReviewService.actionOn(payload);
    }

    @RequestMapping(value = "/review/comment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    void pullRequestReviewCommentPayload(@RequestBody String payload) {
        pullRequestReviewCommentService.actionOn(payload);
    }

    @RequestMapping(value = "/issue/comment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    void pullRequestCommentPayload(@RequestBody String payload) {
        pullRequestCommentService.actionOn(payload);
    }
}
