package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Field;
import java.util.List;

@Document
public class PullRequest {
    public int number;
    public String state;
    public String title;
    public String body;
    public String createdAt;
    public String closedAt;
    public String updatedAt;
    public Boolean merged;
    public String mergedBy;
    public String mergedAt;
    public String sender;

    public User user;
    public Head head;
    public Base base;

    public List<Assignee> assignees;
    public List<Label> labels;
    public List<Comment> comments;
    public List<Review> reviews;

    public int getNumber() {
        return number;
    }

    public List<Assignee> getAssignees() {
        return assignees;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void set(String key, Object value) throws Exception {
        Field field = this.getClass().getField(key);
        field.set(this, value);
    }
}
