package io.hasura.db.util;

import com.google.gson.annotations.SerializedName;

public enum RelType {
    @SerializedName("array")
    ARR_REL,

    @SerializedName("object")
    OBJ_REL;
}
