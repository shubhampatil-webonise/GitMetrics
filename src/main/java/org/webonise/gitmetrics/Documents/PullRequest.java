package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class PullRequest {
    public String closedAt;
    public String mergedAt;
    public Boolean merged;
    public String createdAt;
    public String mergedBy;
    public String title;
    public String body;

    public List<Label> labels;
    public List<Assignee> assignees;
    public List<Comment> comments;
    public List<ReviewComment> reviewComments;
}
