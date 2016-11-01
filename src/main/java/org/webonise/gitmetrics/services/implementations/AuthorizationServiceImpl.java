package org.webonise.gitmetrics.services.implementations;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.services.interfaces.AuthorizationService;
import org.webonise.gitmetrics.services.interfaces.HttpRequestResponseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger logger = Logger.getLogger(AuthorizationServiceImpl.class);

    @Autowired
    private HttpRequestResponseService httpRequestResponseService;

    @Autowired
    private Gson gson;

    @Value("${gitmetrics.org.name}")
    private String organization;

    @Override
    public boolean isAuthorizedUser(String accessToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token " + accessToken);

        try {
            String userDetails = httpRequestResponseService.get("https://api.github.com/user", headers);
            String loginHandle = gson.fromJson(userDetails, JsonObject.class).get("login").getAsString();
            String organizationDetails = httpRequestResponseService.get("https://api.github.com/orgs/" + organization + "/memberships/" + loginHandle, headers);
            String role = gson.fromJson(organizationDetails, JsonObject.class).get("role").getAsString();

            return role.equalsIgnoreCase("admin");
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }

        return false;
    }
}
