package org.webonise.gitmetrics.Controllers;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webonise.gitmetrics.Documents.Repository;
import org.webonise.gitmetrics.Entities.GitRepository;
import org.webonise.gitmetrics.Services.DatabaseService;
import org.webonise.gitmetrics.Services.HttpRequestResponseService;
import org.webonise.gitmetrics.Services.SessionService;

import java.util.List;

@Controller
public class DashboardController {
    private static final Logger logger = Logger.getLogger(DashboardController.class.getName());

    @Autowired
    private HttpRequestResponseService httpRequestResponseService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private Gson gson;

    @Value("${gitmetrics.org.name}")
    private String organization;

    @Autowired
    private DatabaseService databaseService;

    @RequestMapping("/org/repos")
    @ResponseBody
    public String getListOfRepositories() {
        List<GitRepository> gitRepositories = databaseService.findListOfRepositories();
        return gson.toJson(gitRepositories);
    }

    @RequestMapping("/org/repos/{repo}")
    @ResponseBody
    public String getRepositoryData(@PathVariable("repo") String name) {
        Repository repository = databaseService.findRepositoryDetailsByName(name);
        return gson.toJson(repository);
    }
}
