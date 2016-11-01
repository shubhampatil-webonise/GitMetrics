package org.webonise.gitmetrics.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof GitRepository)) return false;

        GitRepository that = (GitRepository) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(objectId, that.objectId)
                .append(name, that.name)
                .append(description, that.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(objectId)
                .append(name)
                .append(description)
                .toHashCode();
    }
}
