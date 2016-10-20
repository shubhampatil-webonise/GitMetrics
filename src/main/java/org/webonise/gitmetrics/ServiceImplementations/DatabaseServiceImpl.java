package org.webonise.gitmetrics.ServiceImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.webonise.gitmetrics.Entities.GitRepository;
import org.webonise.gitmetrics.Repositories.RepositoryList;
import org.webonise.gitmetrics.Services.DatabaseService;

import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private RepositoryList repositoryList;

    @Override
    public List<GitRepository> findListOfRepositories() {
        return repositoryList.findAll();
    }

    public GitRepository findRepositoryDetailsByTitle(String title) {
        return repositoryList.findByTitle();
    }
}
