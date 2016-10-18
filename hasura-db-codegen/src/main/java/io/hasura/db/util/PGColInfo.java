package io.hasura.db.util;

import com.google.gson.annotations.SerializedName;

public class PGColInfo {
    @SerializedName("data_type")
    private PGColType colType;
    @SerializedName("column_name")
    private String colName;

    public PGColInfo(PGColType colType, String colName) {
        this.colType = colType;
        this.colName = colName;
    }

    public PGColType getColType() {
        return this.colType;
    }

    public String getColName() {
        return this.colName;
    }
}
