package org.webonise.gitmetrics.ServiceImplementations;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.webonise.springbootsessiondemo.Service.SessionService;

import javax.servlet.http.HttpSession;

@Service
public class SessionServiceImpl implements SessionService {
    private HttpSession session;

    @Override
    public HttpSession getSession(HttpServletRequest request, boolean createIfNotExist) {
        session = request.getSession(createIfNotExist);
        return session;
    }


    @Override
    public void put(String key, Object data) throws NullPointerException {
        session.setAttribute(key, data);
    }

    @Override
    public Object get(String key) throws NullPointerException {
        return session.getAttribute(key);

    }

    @Override
    public void clear() throws NullPointerException {
        session.invalidate();
    }
}
