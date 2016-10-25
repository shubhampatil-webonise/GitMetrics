package org.webonise.gitmetrics.Services.Webhooks;

import org.springframework.stereotype.Service;

@Service
public interface PullRequestReviewCommentService {
    void actionOn(String payload);
}

