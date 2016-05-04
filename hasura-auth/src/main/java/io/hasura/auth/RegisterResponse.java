package io.hasura.auth;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("hasura_id")
    int hasuraId;

    @SerializedName("hasura_role")
    String hasuraRole;

    @SerializedName("auth_token")
    String auth_token;

    public int getHasuraId() {
        return hasuraId;
    }

    public String getHasuraRole() {
        return hasuraRole;
    }

    public String getSessionId() {
        return auth_token;
    }
}
