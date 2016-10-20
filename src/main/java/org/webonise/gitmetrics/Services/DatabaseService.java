package org.webonise.gitmetrics.Services;

import org.webonise.gitmetrics.Entities.GitRepository;

import java.util.List;

public interface DatabaseService {
    List<GitRepository> findListOfRepositories();

    GitRepository findRepositoryDetailsByTitle(String title);
}
