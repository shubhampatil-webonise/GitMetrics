package org.webonise.gitmetrics.Utilities;

import org.springframework.stereotype.Component;

@Component
public class RepositoryDetails {
    private String owner;
    private String name;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
