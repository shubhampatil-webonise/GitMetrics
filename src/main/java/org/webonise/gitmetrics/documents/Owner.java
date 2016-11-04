package org.webonise.gitmetrics.documents;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Owner {
    private String login;
    private String type;
    private String url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Owner)) return false;

        Owner owner = (Owner) o;

        return new EqualsBuilder()
                .append(login, owner.login)
                .append(type, owner.type)
                .append(url, owner.url)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(login)
                .append(type)
                .append(url)
                .toHashCode();
    }
}
