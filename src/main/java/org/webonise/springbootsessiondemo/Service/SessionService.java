package org.webonise.springbootsessiondemo.Service;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public interface SessionService {
    ModelAndView startSession(Object object, String viewName);

    ModelAndView endSession(HttpSession session, String viewName);
}
