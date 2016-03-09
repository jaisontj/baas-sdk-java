package io.hasura.sample.todo.db.tables.records;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserRecord {
    @SerializedName("id")
    public Integer id;

    @SerializedName("username")
    public String username;

    @SerializedName("tasks")
    public ArrayList<TaskRecord> tasks;

}
