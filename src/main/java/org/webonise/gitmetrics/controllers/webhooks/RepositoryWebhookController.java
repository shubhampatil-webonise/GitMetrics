package org.webonise.gitmetrics.controllers.webhooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.webonise.gitmetrics.services.interfaces.webhooks.RepositoryService;

@RestController
@RequestMapping("/webhooks")
public class RepositoryWebhookController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value = "/repository", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getRepoData(@RequestBody String payload) {
        repositoryService.actionOn(payload);
    }
}
