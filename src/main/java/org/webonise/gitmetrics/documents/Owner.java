package org.webonise.gitmetrics.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Owner {
    public String login;
    public String type;
    public String url;

    public String getLogin() {
        return login;
    }
}
