package org.webonise.gitmetrics.documents;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Review {
    private long id;
    private String body;
    private User user;
    private String submittedAt;
    private List<Comment> comments;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Review)) return false;

        Review review = (Review) o;

        return new EqualsBuilder()
                .append(id, review.id)
                .append(body, review.body)
                .append(user, review.user)
                .append(submittedAt, review.submittedAt)
                .append(comments, review.comments)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(body)
                .append(user)
                .append(submittedAt)
                .append(comments)
                .toHashCode();
    }
}
