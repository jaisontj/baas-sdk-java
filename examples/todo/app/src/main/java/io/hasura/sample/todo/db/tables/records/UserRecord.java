package io.hasura.sample.todo.db.tables.records;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserRecord {
    @SerializedName("hasura_user_id")
    public Integer hasuraUserId;

    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

    @SerializedName("tasks")
    public ArrayList<TaskRecord> tasks;

}
