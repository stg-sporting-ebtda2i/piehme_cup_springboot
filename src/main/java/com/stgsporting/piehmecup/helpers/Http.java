package com.stgsporting.piehmecup.helpers;

import java.io.IOException;
import java.util.Base64;

import net.minidev.json.JSONObject;
import okhttp3.*;
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

    public Response call(String method, JSONObject body) {
        RequestBody requestBody = body != null ? RequestBody.create(
                body.toJSONString(),
                MediaType.parse("application/json")
        ) : null;

        Request request = this.builder
                .method(method, requestBody)
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

    public Response post(JSONObject body) {
        return call("POST", body);
    }

    public Response patch(JSONObject body) {
        return call("PATCH", body);
    }
}
