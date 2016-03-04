package io.hasura.db.util;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class TableInfo {
    @SerializedName("name")
    private String tableName;
    private ArrayList<PGColInfo> columns;
    private ArrayList<RelInfo> relationships;

    public String getTableName() {
        return this.tableName;
    }

    public ArrayList<PGColInfo> getColumns() {
        return this.columns;
    }

    public ArrayList<RelInfo> getRelationships() {
        return this.relationships;
    }
}
