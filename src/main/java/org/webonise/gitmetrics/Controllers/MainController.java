package org.webonise.gitmetrics.Controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.webonise.gitmetrics.Services.SessionService;

@Controller
public class MainController {

    @Autowired
    private SessionService sessionService;

    @Value("${gitmetrics.client.id}")
    private String clientId;

    @Value("${gitmetrics.client.scope}")
    private String scope;

    @Value("${gitmetrics.org.name}")
    private String organization;

    @RequestMapping("/")
    public String renderHomepage(Model model, @RequestParam(required = false) boolean loginFailed, HttpServletRequest request) {
        if (sessionService.getSession(request, false) == null) {
            model.addAttribute("authParams", "client_id=" + clientId + "&scope=" + scope);
            model.addAttribute("loginFailed", loginFailed);
            return "index";
        } else {
            return "redirect:/dashboard";
        }
    }

    @RequestMapping("/dashboard")
    public String renderDashboard(Model model) {
        model.addAttribute("organization", organization);
        return "dashboard";
    }

    @RequestMapping("/logout")
    public String logOut() {
        sessionService.clear();
        return "redirect:/";
    }
}
