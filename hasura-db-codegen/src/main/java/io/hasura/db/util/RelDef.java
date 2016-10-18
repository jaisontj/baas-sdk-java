package io.hasura.db.util;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class RelDef {
    @SerializedName("foreign_key_constraint_on")
    public JsonElement fkOn;

}
