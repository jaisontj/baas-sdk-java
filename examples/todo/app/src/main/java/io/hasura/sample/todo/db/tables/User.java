package io.hasura.sample.todo.db.tables;

import com.google.gson.reflect.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Timestamp;
import io.hasura.db.*;
import io.hasura.sample.todo.db.tables.records.*;
public class User extends Table<UserRecord> {
    public static final User USER = new User();

    public User() {
        super("user");
    }

    public Type getInsResType() {
        return new TypeToken<InsertResult<UserRecord>>() {}.getType();
    }

    public Type getSelResType() {
        return new TypeToken<ArrayList<UserRecord>>() {}.getType();
    }

    public Type getUpdResType() {
        return new TypeToken<UpdateResult<UserRecord>>() {}.getType();
    }

    public Type getDelResType() {
        return new TypeToken<DeleteResult<UserRecord>>() {}.getType();
    }

    public final PGField<UserRecord, Integer> HASURA_USER_ID = new PGField<>("hasura_user_id");
    public final PGField<UserRecord, String> NAME = new PGField<>("name");
    public final PGField<UserRecord, String> EMAIL = new PGField<>("email");

    public final ArrayRelationship<UserRecord, TaskRecord> TASKS = new ArrayRelationship<>("tasks");
}
