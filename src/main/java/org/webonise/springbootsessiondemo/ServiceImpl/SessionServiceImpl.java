package org.webonise.springbootsessiondemo.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.webonise.springbootsessiondemo.Service.SessionService;

import javax.servlet.http.HttpSession;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    ModelAndView model;

    @Override
    public ModelAndView startSession(Object object, String viewName) {
        model.addObject("user", object);
        model.setViewName(viewName);
        return model;
    }

    @Override
    public ModelAndView endSession(HttpSession session, String viewName) {
        model.setViewName(viewName);
        session.invalidate();
        return model;
    }
}
