package org.webonise.springbootsessiondemo.Controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebhookController {
    String Data, event;

    @RequestMapping(value = "/payload", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String getData(@RequestBody String data, HttpServletRequest request) {
        Data = data;
        event = (String) request.getHeader("X-GitHub-Event");
        System.out.println(Data);
        System.out.println(event);
        return "OK";
    }

    @RequestMapping(value = "/payloadData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String data() {
        System.out.println(event);
        System.out.println(Data);
        return Data;
    }
}
