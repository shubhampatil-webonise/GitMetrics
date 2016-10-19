package org.webonise.springbootsessiondemo.Controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.webonise.springbootsessiondemo.Service.SessionService;

@Controller
public class DashboardController {
    @Autowired
    private SessionService service;
    private ModelAndView model;
    public static final String Key = "Username";

    @RequestMapping("/Dashboard")
    public ModelAndView rememberThought(@RequestParam(required = false) String username, @RequestParam(required = false) String password, HttpServletRequest request) {
        model = new ModelAndView();
        if (service.getSession(request, false) != null) {
            System.out.println("in previous sess");
            model.addObject("Username", service.getDataInSession(Key));
            model.setViewName("Dashboard");
        } else if (username != null && password != null && username.equalsIgnoreCase("Shirish") && password.equalsIgnoreCase("abc123")) {
            service.getSession(request, true);
            service.putDataInSession(Key, username);
            model.addObject("Username", service.getDataInSession(Key));
            model.setViewName("Dashboard");
        } else {
            model.setViewName("Error");
        }
        return model;
    }

    @RequestMapping("/endsession")
    public ModelAndView check() {
        model = new ModelAndView();
        service.endSession();
        model.setViewName("redirect:/");
        return model;
    }
}
