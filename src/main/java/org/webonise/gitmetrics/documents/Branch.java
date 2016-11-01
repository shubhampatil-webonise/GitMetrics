package org.webonise.gitmetrics.documents;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Branch {
    private String ref;
    private String sender;
    private Boolean stale;
    private Boolean mailSent;

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Boolean getStale() {
        return stale;
    }

    public Boolean getMailSent() {
        return mailSent;
    }

    public void setStale(Boolean stale) {
        this.stale = stale;
    }

    public void setMailSent(Boolean mailSent) {
        this.mailSent = mailSent;
    }

    public String getRef() {
        return ref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Branch)) return false;

        Branch branch = (Branch) o;

        return new EqualsBuilder()
                .append(ref, branch.ref)
                .append(sender, branch.sender)
                .append(stale, branch.stale)
                .append(mailSent, branch.mailSent)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(ref)
                .append(sender)
                .append(stale)
                .append(mailSent)
                .toHashCode();
    }
}
