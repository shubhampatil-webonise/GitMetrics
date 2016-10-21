package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Collaborator {
    public String login;
    public String type;
}
