package org.webonise.gitmetrics.Services.Webhooks;

import org.springframework.stereotype.Service;

@Service
public interface PullRequestReviewService {
    void actionOn(String payload);
}

