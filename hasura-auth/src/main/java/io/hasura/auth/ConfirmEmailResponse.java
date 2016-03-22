package io.hasura.auth;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ConfirmEmailResponse {
    @SerializedName("user-id")
    String user_id;
    @SerializedName("user-email")
    String email;
    @SerializedName("message")
    String message;
}
