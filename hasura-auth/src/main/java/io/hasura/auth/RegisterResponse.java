package io.hasura.auth;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("hasura_id")
    int hasuraId;

    @SerializedName("hasura_role")
    String hasuraRole;

    @SerializedName("session_id")
    String sessionId;

    public int getHasuraId() {
        return hasuraId;
    }

    public String getHasuraRole() {
        return hasuraRole;
    }

    public String getSessionId() {
        return sessionId;
    }
}
