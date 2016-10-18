package io.hasura.db.util;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class UnexpectedSchema extends Exception {
    public UnexpectedSchema(String msg){
        super(msg);
    }
}

public class TableInfo {
    @SerializedName("table_name")
    private String tableName;
    private ArrayList<PGColInfo> columns;
    private ArrayList<RelInfo> relationships;
    @SerializedName("foreign_key_constraints")
    public ArrayList<FKInfo> fkConstraints;

    public String getTableName() {
        return this.tableName;
    }

    public ArrayList<PGColInfo> getColumns() {
        return this.columns;
    }

    public ArrayList<RelInfo> getRelationships() {
        return this.relationships;
    }

    public String getRemoteTable(RelInfo relInfo) throws UnexpectedSchema {
        String remoteTable = null;
        switch (relInfo.getRelType()) {
        case OBJ_REL:
            String fkOnCol = relInfo.relDef.fkOn.getAsString();
            for (FKInfo cons : this.fkConstraints) {
                if (cons.colMapping.containsKey(fkOnCol) && cons.colMapping.size() == 1) {
                    return cons.refTableName;
                }
            }
            throw new UnexpectedSchema("obj relationship's remote table couldn't be determined");
        case ARR_REL:
            remoteTable = relInfo.relDef.fkOn
                .getAsJsonObject()
                .getAsJsonPrimitive("table")
                .getAsString();
        }
        if (remoteTable == null)
            throw new UnexpectedSchema("relationship's remote table couldn't be determined");
        return remoteTable;
    }
}
