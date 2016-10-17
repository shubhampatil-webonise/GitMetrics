package org.webonise.gitmetrics.ServiceImpl;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.webonise.gitmetrics.Services.HttpRequestResponse;

import java.io.IOException;
import java.util.Map;

public class OkHttpRequestResponse implements HttpRequestResponse {
    private final OkHttpClient client = new OkHttpClient();

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code" + response);
        }

        String responseBody = response.body().string();

        return responseBody;
    }

    public String get(String url, Map<String, String> headers) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code" + response);
        }

        String responseBody = response.body().string();

        return responseBody;
    }

    public String post(String url, MediaType mediaType, String content) throws IOException {
        RequestBody requestBody = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code" + response);
        }

        String responseBody = response.body().string();

        return responseBody;
    }

    public String post(String url, MediaType mediaType, Map<String, String> headers, String content) throws IOException {
        RequestBody requestBody = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code" + response);
        }

        String responseBody = response.body().string();

        return responseBody;
    }
}
