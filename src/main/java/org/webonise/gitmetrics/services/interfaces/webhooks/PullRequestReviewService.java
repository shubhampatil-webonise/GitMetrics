package org.webonise.gitmetrics.services.interfaces.webhooks;

import org.springframework.stereotype.Service;

@Service
public interface PullRequestReviewService {
    void actionOn(String payload);
}

