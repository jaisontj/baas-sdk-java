package io.hasura.auth;

import io.hasura.core.Call;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import okhttp3.JavaNetCookieJar;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class AuthService {

    private static final Gson gson = new GsonBuilder().create();
    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient httpClient;
    private String dbUrl;

    public AuthService(String dbUrl) {
        this.dbUrl = dbUrl;
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        this.httpClient =
            new OkHttpClient.Builder()
                            .cookieJar(new JavaNetCookieJar(cookieManager))
                            .build();
    }

    public OkHttpClient getClient() {
        return this.httpClient;
    }

    public String getUrl() {
        return this.dbUrl;
    }

    private <T> Call<T, AuthException> mkCall(String url, String jsonBody, Type bodyType) {
        RequestBody reqBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
            .url(this.dbUrl + url)
            .post(reqBody)
            .build();
        Call<T, AuthException> newCall
            = new Call<T, AuthException>(
                    httpClient.newCall(request), new AuthResponseConverter<T>(bodyType));
        return newCall;
    }

    public Call<RegisterResponse, AuthException> register(RegisterRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<RegisterResponse>() {}.getType();
        return mkCall("/auth/signup", jsonBody, respType);
    }

    public Call<LoginResponse, AuthException> login(LoginRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<LoginResponse>() {}.getType();
        return mkCall("/auth/login", jsonBody, respType);
    }

    public Call<LoginResponse, AuthException> login(
         String userName, String password, JsonObject info) {
        return this.login(new LoginRequest(userName, password, info));
    }
}
