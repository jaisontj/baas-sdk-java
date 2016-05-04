package io.hasura.auth;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class GetCredentialsResponse {
    @SerializedName("hasura_id")
    int hasuraId;

    @SerializedName("hasura_role")
    String hasuraRole;

    @SerializedName("info")
    JsonObject info;

    @SerializedName("auth_token")
    String auth_token;

    public int getHasuraId() {
        return hasuraId;
    }

    public String getHasuraRole() {
        return hasuraRole;
    }

    public JsonObject getInfo() {
        return info;
    }

    public String getSessionId() {
        return auth_token;
    }
}
