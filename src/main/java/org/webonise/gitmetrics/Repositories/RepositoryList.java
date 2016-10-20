package org.webonise.gitmetrics.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.webonise.gitmetrics.Entities.GitRepository;

import java.util.List;

public interface RepositoryList extends CrudRepository<GitRepository, Long> {
    List<GitRepository> findAll();

    GitRepository findByTitle();
}
