package org.webonise.gitmetrics.Services.Webhooks;

import org.springframework.stereotype.Service;

@Service
public interface PullRequestService {
    void actionOn(String payload);
}

