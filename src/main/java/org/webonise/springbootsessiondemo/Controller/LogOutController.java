package org.webonise.springbootsessiondemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.webonise.springbootsessiondemo.Service.SessionService;

import javax.servlet.http.HttpSession;

@RestController
public class LogOutController {
    @Autowired
    SessionService service;
    ModelAndView model;

    @RequestMapping("/")
    public ModelAndView home(HttpSession session) {
        model = new ModelAndView("HomePage");
        String sessionData = (String) session.getAttribute("user");
        if (sessionData != null) {
            model.addObject((session.getAttribute("user")));
            model.setViewName("redirect:Dashboard");
        }
        return model;
    }

    @RequestMapping("/endsession")
    public ModelAndView check(HttpSession session) {
        model = service.endSession(session, "redirect:/");
        return model;
    }
}
