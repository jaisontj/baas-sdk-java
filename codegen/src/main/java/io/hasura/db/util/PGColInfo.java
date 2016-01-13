package io.hasura.db.util;

import java.util.HashMap;
import com.google.gson.annotations.SerializedName;

public class PGColInfo {
    @SerializedName("type")
    private PGColType colType;

    public PGColInfo(PGColType colType) {
        this.colType = colType;
    }

    public PGColType getColType() {
        return this.colType;
    }
}
