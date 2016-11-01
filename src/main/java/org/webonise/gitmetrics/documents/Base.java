package org.webonise.gitmetrics.documents;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Base {
    private String ref;
    private User user;
    private Repository repo;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Repository getRepo() {
        return repo;
    }

    public void setRepo(Repository repo) {
        this.repo = repo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Base)) return false;

        Base base = (Base) o;

        return new EqualsBuilder()
                .append(ref, base.ref)
                .append(user, base.user)
                .append(repo, base.repo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(ref)
                .append(user)
                .append(repo)
                .toHashCode();
    }
}
