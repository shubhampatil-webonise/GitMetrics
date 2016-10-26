package org.webonise.gitmetrics.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.webonise.gitmetrics.Documents.Repository;

import java.util.List;

public interface RepositoryCollection extends MongoRepository<Repository, String> {
    List<Repository> findByName(String name);

    void deleteRepositoryByName(String name);
}
