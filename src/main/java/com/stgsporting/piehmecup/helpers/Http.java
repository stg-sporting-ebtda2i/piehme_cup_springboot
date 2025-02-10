package com.stgsporting.piehmecup.helpers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;


public class Http {

    private final Request.Builder builder;

    public Http(String url) {
        this.builder = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json");
    }

    public Http(String url, String key, String secret) {
        this.builder = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json");

        basicAuthentication(key, secret);
    }

    public Request.Builder addHeader(String key, String value) {
        return builder.addHeader(key, value);
    }

    public void basicAuthentication(String key, String secret) {
        String token = Base64.getEncoder().encodeToString((key + ":" + secret).getBytes());

        addHeader("Authorization", token);
    }

    public Response call(String method, RequestBody body) {
        Request request = this.builder
                .method(method, body)
                .build();

        OkHttpClient client = new OkHttpClient();

        Response res = new Response();
        try (okhttp3.Response response = client.newCall(request).execute()) {

            ResponseBody responseBody = response.body();

            if (responseBody != null) {
                res.setBody(responseBody.string());
            }

            res.setStatusCode(HttpStatus.valueOf(response.code()));

        } catch (IOException e) {
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return res;
    }

    public Response get() {
        return call("GET", null);
    }

    public Response post(RequestBody body) {
        return call("POST", body);
    }

    public Response patch(RequestBody body) {
        return call("PATCH", body);
    }
}
