package org.webonise.gitmetrics.Services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JsonParser {
    String parse(String jsonBody, List<String> keys);

    String parse(String jsonBody, String key);
}
