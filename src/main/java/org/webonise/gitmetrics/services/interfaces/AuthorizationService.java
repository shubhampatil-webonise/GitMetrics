package org.webonise.gitmetrics.services.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {
    boolean isAuthorizedUser(String accessToken);
}
