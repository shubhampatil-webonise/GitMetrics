package org.webonise.gitmetrics.documents;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Field;
import java.util.List;

@Document
public class PullRequest {
    private int number;
    private String state;
    private String title;
    private String body;
    private String createdAt;
    private String closedAt;
    private String updatedAt;
    private Boolean merged;
    private String mergedBy;
    private String mergedAt;
    private String sender;

    private User user;
    private Head head;
    private Base base;

    private List<Assignee> assignees;
    private List<Label> labels;
    private List<Comment> comments;
    private List<Review> reviews;

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
        Field field = this.getClass().getDeclaredField(key);
        field.set(this, value);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getMerged() {
        return merged;
    }

    public void setMerged(Boolean merged) {
        this.merged = merged;
    }

    public String getMergedBy() {
        return mergedBy;
    }

    public void setMergedBy(String mergedBy) {
        this.mergedBy = mergedBy;
    }

    public String getMergedAt() {
        return mergedAt;
    }

    public void setMergedAt(String mergedAt) {
        this.mergedAt = mergedAt;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public void setAssignees(List<Assignee> assignees) {
        this.assignees = assignees;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PullRequest)) return false;

        PullRequest that = (PullRequest) o;

        return new EqualsBuilder()
                .append(number, that.number)
                .append(state, that.state)
                .append(title, that.title)
                .append(body, that.body)
                .append(createdAt, that.createdAt)
                .append(closedAt, that.closedAt)
                .append(updatedAt, that.updatedAt)
                .append(merged, that.merged)
                .append(mergedBy, that.mergedBy)
                .append(mergedAt, that.mergedAt)
                .append(sender, that.sender)
                .append(user, that.user)
                .append(head, that.head)
                .append(base, that.base)
                .append(assignees, that.assignees)
                .append(labels, that.labels)
                .append(comments, that.comments)
                .append(reviews, that.reviews)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(number)
                .append(state)
                .append(title)
                .append(body)
                .append(createdAt)
                .append(closedAt)
                .append(updatedAt)
                .append(merged)
                .append(mergedBy)
                .append(mergedAt)
                .append(sender)
                .append(user)
                .append(head)
                .append(base)
                .append(assignees)
                .append(labels)
                .append(comments)
                .append(reviews)
                .toHashCode();
    }
}
