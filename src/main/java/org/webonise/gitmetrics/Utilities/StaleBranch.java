package org.webonise.gitmetrics.Utilities;

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


}
