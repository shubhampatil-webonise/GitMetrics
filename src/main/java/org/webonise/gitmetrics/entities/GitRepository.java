package org.webonise.gitmetrics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GitRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String objectId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    protected GitRepository() {
    }

    public GitRepository(String objectId, String name, String description) {
        this.objectId = objectId;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String toString() {
        return String.format("Repository [id=%d, name=%s, desc=%s]", objectId, name, description);
    }
}
