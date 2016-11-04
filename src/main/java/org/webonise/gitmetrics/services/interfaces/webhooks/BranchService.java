package org.webonise.gitmetrics.services.interfaces.webhooks;

import org.springframework.stereotype.Service;

@Service
public interface BranchService {
    void actionOnCreate(String payload);

    void actionOnDelete(String payload);
}
