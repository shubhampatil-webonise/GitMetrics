package org.webonise.gitmetrics.documents;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "Repositories")
public class Repository {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;
    private String sender;
    private String createdAt;
    private String updatedAt;
    private String url;
    private Boolean privateRepo;
    private Owner owner;

    private List<Collaborator> collaborators;
    private List<Branch> branches;
    private List<PullRequest> pullRequests;

    public Owner getOwner() {
        return owner;
    }

    protected Repository() {

    }

    public String getName() {
        return name;
    }

    public String getSender() {
        return sender;
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

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getPrivateRepo() {
        return privateRepo;
    }

    public void setPrivateRepo(Boolean privateRepo) {
        this.privateRepo = privateRepo;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public void setPullRequests(List<PullRequest> pullRequests) {
        this.pullRequests = pullRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Repository)) return false;

        Repository that = (Repository) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(description, that.description)
                .append(sender, that.sender)
                .append(createdAt, that.createdAt)
                .append(updatedAt, that.updatedAt)
                .append(url, that.url)
                .append(privateRepo, that.privateRepo)
                .append(owner, that.owner)
                .append(collaborators, that.collaborators)
                .append(branches, that.branches)
                .append(pullRequests, that.pullRequests)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(description)
                .append(sender)
                .append(createdAt)
                .append(updatedAt)
                .append(url)
                .append(privateRepo)
                .append(owner)
                .append(collaborators)
                .append(branches)
                .append(pullRequests)
                .toHashCode();
    }
}
