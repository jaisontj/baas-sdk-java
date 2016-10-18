package io.hasura.db.util;

import com.google.gson.annotations.SerializedName;

public class RelInfo {
    @SerializedName("rel_type")
    private RelType relType;

    @SerializedName("rel_def")
    public RelDef relDef;

    @SerializedName("rel_name")
    private String relName;

    public RelType getRelType() {
        return this.relType;
    }

    public String getRelName() {
        return this.relName;
    }
}
