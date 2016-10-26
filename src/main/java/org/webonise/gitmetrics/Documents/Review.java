package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Review {
    public long id;
    public String body;
    public User user;
    public String submittedAt;
    public List<Comment> comments;

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
