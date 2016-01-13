package io.hasura.db.util;

import java.util.HashMap;
import com.google.gson.annotations.SerializedName;

public class TableInfo {
    @SerializedName("name")
    private String tableName;
    private HashMap<String, PGColInfo> columns;
    private HashMap<String, RelInfo> relationships;

    public String getTableName() {
        return this.tableName;
    }

    public HashMap<String, PGColInfo> getColumns() {
        return this.columns;
    }

    public HashMap<String, RelInfo> getRelationships() {
        return this.relationships;
    }
}
