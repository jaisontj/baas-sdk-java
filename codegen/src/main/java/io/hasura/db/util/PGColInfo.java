package io.hasura.db.util;

import java.util.HashMap;
import com.google.gson.annotations.SerializedName;

public class PGColInfo {
    @SerializedName("type")
    private PGColType colType;

    public PGColInfo(PGColType colType, String colName) {
        this.colType = colType;
        this.colName = colName;
    }

    public PGColType getColType() {
        return this.colType;
    }

    @SerializedName("name")
    private String colName;

    public String getColName() {
        return this.colName;
    }
}
