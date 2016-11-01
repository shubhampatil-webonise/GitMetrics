package org.webonise.gitmetrics.services.interfaces;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface HttpRequestResponseService {
    String get(String url) throws IOException;

    String get(String url, Map<String, String> headers) throws IOException;

    String post(String url, Map<String, String> body) throws IOException;

    String post(String url, Map<String, String> headers, Map<String, String> body) throws IOException;
}

