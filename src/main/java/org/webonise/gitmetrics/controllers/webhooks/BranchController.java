package org.webonise.gitmetrics.controllers.webhooks;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webonise.gitmetrics.services.interfaces.webhooks.BranchService;

@Controller
@RequestMapping("/webhooks/repository/branch")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void getCreatedBranchInfo(@RequestBody String payload, HttpServletRequest request) {
        branchService.actionOnCreate(payload);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void getDeletedBranchInfo(@RequestBody String payload, HttpServletRequest request) {
        branchService.actionOnDelete(payload);
    }
}
