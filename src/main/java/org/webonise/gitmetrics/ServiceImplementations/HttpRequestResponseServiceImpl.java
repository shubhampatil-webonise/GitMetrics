package org.webonise.gitmetrics.ServiceImplementations;

import com.google.gson.Gson;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Services.HttpRequestResponseService;

import java.io.IOException;
import java.util.Map;

@Component
public class HttpRequestResponseServiceImpl implements HttpRequestResponseService {

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private Gson gson;

    @Override
    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code :" + response);
        }

        return response.body().string();
    }

    @Override
    public String get(String url, Map<String, String> headers) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code :" + response);
        }

        return response.body().string();
    }

    @Override
    public String post(String url, Map<String, String> body) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(body));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code :" + response);
        }

        return response.body().string();
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> body) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(body));
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code :" + response);
        }

        return response.body().string();
    }
}
