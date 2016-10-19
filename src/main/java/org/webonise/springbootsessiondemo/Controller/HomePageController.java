package org.webonise.springbootsessiondemo.Controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.webonise.springbootsessiondemo.Service.SessionService;

import javax.servlet.http.HttpSession;

@RestController
public class HomePageController {
    @Autowired
    private SessionService service;
    private ModelAndView model;
    private HttpSession session;

    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request) {
        model = new ModelAndView();
        session = service.getSession(request, false);
        if (session == null) {
            model.setViewName("HomePage");
        } else {
            model.addObject((session.getAttribute(DashboardController.Key)));
            model.setViewName("redirect:Dashboard");
        }
        return model;
    }
}
