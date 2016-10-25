package org.webonise.gitmetrics.Services.Webhooks;

import org.springframework.stereotype.Service;

@Service
public interface PullRequestCommentService {
    void actionOn(String payload);
}

