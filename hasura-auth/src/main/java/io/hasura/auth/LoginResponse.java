package io.hasura.auth;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("hasura_id")
    int hasuraId;

    @SerializedName("hasura_role")
    String hasuraRole;

    @SerializedName("auth_token")
    String auth_token;

     @SerializedName("info")
    JsonObject info;

    public int getHasuraId() {
        return hasuraId;
    }

    public String getHasuraRole() {
        return hasuraRole;
    }

    public String getSessionId() {
        return auth_token;
    }

    public JsonObject getInfo() {
        return info;
    }
}
