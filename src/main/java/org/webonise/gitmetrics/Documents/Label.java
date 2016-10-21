package org.webonise.gitmetrics.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Label {
    String name;
    String color;
    String sender;
}
