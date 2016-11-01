package org.webonise.gitmetrics.services.interfaces;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

public interface SessionService {

    HttpSession getSession(HttpServletRequest request, boolean createIfNotExist);
    
    void put(String key, Object value);

    Object get(String key);

    void clear();
}
