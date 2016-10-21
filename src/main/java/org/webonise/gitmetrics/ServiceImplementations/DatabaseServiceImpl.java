package org.webonise.gitmetrics.ServiceImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Documents.Repository;
import org.webonise.gitmetrics.Entities.GitRepository;
import org.webonise.gitmetrics.Repositories.RepositoryCollection;
import org.webonise.gitmetrics.Repositories.RepositoryList;
import org.webonise.gitmetrics.Services.DatabaseService;

import java.util.List;

@Component
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private RepositoryList repositoryList;

    @Autowired
    private RepositoryCollection repositoryCollection;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<GitRepository> findListOfRepositories() {
        return repositoryList.findAll();
    }

    @Override
    public List<Repository> findRepositoryDetailsByName(String name) {
        List<Repository> repositories = repositoryCollection.findByName(name);
        return repositories;
    }
}
