package org.webonise.gitmetrics.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Label {
    String name;
    String color;
    String sender;

    public String getName() {
        return name;
    }
}
