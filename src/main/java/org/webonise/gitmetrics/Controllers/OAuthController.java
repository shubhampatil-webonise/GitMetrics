package org.webonise.gitmetrics.Controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.webonise.gitmetrics.Services.AuthorizationService;
import org.webonise.gitmetrics.Services.HttpRequestResponseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OAuthController {

    private static final Logger logger = Logger.getLogger(OAuthController.class.getName());

    @Autowired
    HttpRequestResponseService httpRequestResponseService;

    @Autowired
    AuthorizationService authorizationService;

    @Value("${gitmetrics.client.id}")
    private String clientId;

    @Value("${gitmetrics.client.secret}")
    private String clientSecret;

    @Value("${gitmetrics.client.scope}")
    private String scope;

    @RequestMapping("/callback")
    public String oauthCallback(@RequestParam String code, RedirectAttributes redirectAttributes) {

        Map<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        requestBody.put("code", code);

        try {
            String response = httpRequestResponseService.post("https://github.com/login/oauth/access_token", requestBody);
            return "redirect:/authorize?" + response;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        redirectAttributes.addAttribute("loginFailed", true);
        return "redirect:/";
    }

    @RequestMapping("/authorize")
    public String authorizeUser(@RequestParam("access_token") String accessToken, RedirectAttributes redirectAttributes) {
        if (authorizationService.isAuthorizedUser(accessToken)) {
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addAttribute("loginFailed", true);
            return "redirect:/";
        }
    }
}
