package org.webonise.gitmetrics.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.webonise.gitmetrics.Documents.Repository;

public interface RepositoryCollection extends MongoRepository<Repository, String> {
    Repository findByName(String name);

    void deleteRepositoryByName(String name);
}
