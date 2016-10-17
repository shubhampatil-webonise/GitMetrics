package org.webonise.gitmetrics.Services;

import okhttp3.MediaType;

import java.io.IOException;
import java.util.Map;

public interface HttpRequestResponse {
    String get(String url) throws IOException;

    String get(String url, Map<String, String> headers) throws IOException;

    String post(String url, MediaType mediaType, String content) throws IOException;

    String post(String url, MediaType mediaType, Map<String, String> headers, String content) throws IOException;
}

