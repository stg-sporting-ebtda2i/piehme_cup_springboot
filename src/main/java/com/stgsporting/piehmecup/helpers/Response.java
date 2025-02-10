package com.stgsporting.piehmecup.helpers;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class Response {
    private String body;
    private HttpStatus statusCode;
}
