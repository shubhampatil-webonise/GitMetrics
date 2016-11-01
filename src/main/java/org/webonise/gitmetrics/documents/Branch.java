package org.webonise.gitmetrics.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Branch {
    public String ref;
    public String sender;
    public Boolean stale;
    public Boolean mailSent;

    public void setStale(Boolean stale) {
        this.stale = stale;
    }

    public void setMailSent(Boolean mailSent) {
        this.mailSent = mailSent;
    }

    public String getRef() {
        return ref;
    }
}
