package org.webonise.gitmetrics.utilities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

@Component
public class StaleBranch {
    private String name;
    private String last_committer;
    private String date;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_committer() {
        return last_committer;
    }

    public void setLast_committer(String last_committer) {
        this.last_committer = last_committer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof StaleBranch)) return false;

        StaleBranch that = (StaleBranch) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .append(last_committer, that.last_committer)
                .append(date, that.date)
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(last_committer)
                .append(date)
                .append(email)
                .toHashCode();
    }
}
