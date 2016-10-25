package org.webonise.gitmetrics.Controllers.Webhooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.webonise.gitmetrics.Services.Webhooks.RepositoryService;

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
