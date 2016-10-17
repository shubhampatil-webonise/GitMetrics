package org.webonise.gitmetrics.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OAuthController {

    @Value("${gitmetrics_client_id}")
    private String client_id;

    @Value("${gitmetrics_client_secret}")
    private String client_secret;

    @RequestMapping("/callback")
    public String OAuthCallback(@RequestParam String code) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

        map.add("client_id", client_id);
        map.add("client_secret", client_secret);
        map.add("code", code);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject("https://github.com/login/oauth/access_token", map, String.class);
        
        return "redirect:/dashboard";
    }
}
