package org.webonise.gitmetrics.services.interfaces.webhooks;

import org.springframework.stereotype.Service;

@Service
public interface CollaboratorService {
    void actionOnCreate(String payload);
}
