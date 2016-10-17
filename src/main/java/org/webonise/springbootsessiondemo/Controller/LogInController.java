package org.webonise.springbootsessiondemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.webonise.springbootsessiondemo.Service.SessionService;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class LogInController {
    @Autowired
    SessionService service;

    @RequestMapping("/Dashboard")
    public ModelAndView rememberThought(@RequestParam(required = false) String username, @RequestParam(required = false) String password, HttpSession session) {
        ModelAndView model = new ModelAndView();
        if (session.getAttribute("user") != null) {
            model.addObject(session.getAttribute("user"));
            model.setViewName("Dashboard");
        } else if (username.equalsIgnoreCase("Shirish") && password.equalsIgnoreCase("abc123")) {
            model = service.startSession(username, "Dashboard");
        } else {
            model.setViewName("Error");
        }
        return model;
    }
}
