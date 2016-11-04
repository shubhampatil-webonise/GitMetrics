package org.webonise.gitmetrics.controllers.webhooks;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webonise.gitmetrics.services.interfaces.webhooks.CollaboratorService;

@Controller
@RequestMapping("/webhooks/repository")
public class CollaboratorController {
    @Autowired
    private CollaboratorService service;

    @RequestMapping(value = "/collaborator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addNewCollaborator(@RequestBody String payload, HttpServletRequest request) {
        service.actionOnCreate(payload);
    }
}
