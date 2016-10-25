package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Owner {
    public String login;
    public String type;
    public String url;
}
