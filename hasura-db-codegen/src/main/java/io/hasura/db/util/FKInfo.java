package io.hasura.db.util;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class FKInfo {
    @SerializedName("constraint_name")
    public String consName;

    @SerializedName("table_name")
    public String tableName;

    @SerializedName("ref_table")
    public String refTableName;

    @SerializedName("column_mapping")
    public Map<String, String> colMapping;
}
