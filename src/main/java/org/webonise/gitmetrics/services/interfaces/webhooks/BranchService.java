package org.webonise.gitmetrics.services.interfaces.webhooks;

public interface BranchService {
    void actionOnCreate(String payload);

    void actionOnDelete(String payload);
}
