package org.webonise.gitmetrics.Services.Webhooks;

public interface BranchService {
    void actionOnCreate(String payload);

    void actionOnDelete(String payload);
}
