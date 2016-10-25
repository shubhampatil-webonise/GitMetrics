package org.webonise.gitmetrics.Services;

import org.springframework.stereotype.Service;
import org.webonise.gitmetrics.Documents.Repository;
import org.webonise.gitmetrics.Entities.GitRepository;

import java.util.List;

@Service
public interface DatabaseService {
    List<GitRepository> findListOfRepositories();

    List<Repository> findRepositoryDetailsByName(String name);
}
