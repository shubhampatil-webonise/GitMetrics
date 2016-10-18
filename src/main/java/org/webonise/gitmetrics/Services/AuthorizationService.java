package org.webonise.gitmetrics.Services;

import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {
    boolean isAuthorizedUser(String accessToken);
}
