package io.hasura.auth;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class SocialLoginResponse {
    @SerializedName("hasura_id")
    int hasuraId;

    @SerializedName("hasura_role")
    String hasuraRole;

    @SerializedName("new_user")
    boolean newUser;

    @SerializedName("session_id")
    String sessionId;

    @SerializedName("info")
    JsonObject info;

    public int getHasuraId() {
        return hasuraId;
    }

    public String getHasuraRole() {
        return hasuraRole;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isNewUser() {
      return newUser;
    }

    public JsonObject getInfo() {
        return info;
    }
}
