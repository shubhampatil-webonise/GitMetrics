package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "Repositories")
public class Repository {

    @Id
    public String id;
    public String name;
    public String description;
    public String sender;
    public String createdAt;
    public String updatedAt;
    public String url;
    public Boolean privateRepo;
    public Owner owner;

    public List<Collaborator> collaborators;
    public List<Branch> branches;
    public List<PullRequest> pullRequests;

    protected Repository() {
    }

    public Repository(String name, String description, String sender, String createdAt, String updatedAt, String url, Boolean privateRepo, Owner owner, List<Collaborator> collaborators, List<Branch> branches, List<PullRequest> pullRequests) {
        this.name = name;
        this.description = description;
        this.sender = sender;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.url = url;
        this.privateRepo = privateRepo;
        this.owner = owner;
        this.collaborators = collaborators;
        this.branches = branches;
        this.pullRequests = pullRequests;
    }

    public String getId() {
        return id;
    }

    public List<PullRequest> getPullRequests() {
        return pullRequests;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
