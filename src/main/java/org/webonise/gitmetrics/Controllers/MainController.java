package org.webonise.gitmetrics.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Value("${gitmetrics_client_id}")
    private String client_id;

    @RequestMapping("/")
    public String renderHomepage(Model model) {
        model.addAttribute("client_id", client_id);
        return "index";
    }

    @RequestMapping("/dashboard")
    public String renderDashboard(Model model) {
        return "dashboard";
    }
}
