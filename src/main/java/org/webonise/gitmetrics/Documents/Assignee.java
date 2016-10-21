package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Assignee {
    public String login;
    public String type;
    public String sender;
}
