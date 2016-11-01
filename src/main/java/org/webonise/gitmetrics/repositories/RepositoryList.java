package org.webonise.gitmetrics.repositories;

import org.springframework.data.repository.CrudRepository;
import org.webonise.gitmetrics.entities.GitRepository;

import java.util.List;

public interface RepositoryList extends CrudRepository<GitRepository, Long> {
    List<GitRepository> findAll();

    void deleteGitRepositoryByName(String name);
}
