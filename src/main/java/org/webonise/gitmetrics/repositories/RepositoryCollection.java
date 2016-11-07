package org.webonise.gitmetrics.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.webonise.gitmetrics.documents.Repository;

public interface RepositoryCollection extends MongoRepository<Repository, String> {
    Repository findByName(String name);

    void deleteRepositoryByName(String name);
}
