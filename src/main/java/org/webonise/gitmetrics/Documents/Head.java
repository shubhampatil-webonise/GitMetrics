package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Head {
    public String ref;
    public User user;
    public Repository repo;
}
