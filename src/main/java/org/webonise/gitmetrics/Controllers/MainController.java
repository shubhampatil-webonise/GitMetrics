package org.webonise.gitmetrics.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Value("${gitmetrics.client.id}")
    private String clientId;

    @Value("${gitmetrics.client.scope}")
    private String scope;

    @RequestMapping("/")
    public String renderHomepage(Model model, @RequestParam(required = false) boolean loginFailed) {
        model.addAttribute("authParams", "client_id=" + clientId + "&scope=" + scope);
        model.addAttribute("loginFailed", loginFailed);
        return "index";
    }

    @RequestMapping("/dashboard")
    public String renderDashboard() {
        return "dashboard";
    }
}
