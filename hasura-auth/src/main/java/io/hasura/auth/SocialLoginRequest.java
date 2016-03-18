package io.hasura.auth;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class SocialLoginRequest {
    String provider;
    String accessToken;

    public SocialLoginRequest(String provider, String token) {
        this.provider = provider;
        this.accessToken = token;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }
}
