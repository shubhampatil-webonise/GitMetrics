package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Branch {
    public String ref;
    public String sender;
    public Boolean stale;
    public Boolean mailSent;
}
