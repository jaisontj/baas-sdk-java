package io.hasura.db;

import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.ArrayList;

import io.hasura.db.delete.DeleteResult;
import io.hasura.db.insert.InsertResult;
import io.hasura.db.update.UpdateResult;

class FakUser extends Table<FakUserRecord> {

    public static final FakUser FAK_USER = new FakUser();

    public FakUser() {
        super("fak_user");
    }

    public Type getInsResType() {
        return new TypeToken<InsertResult<FakUserRecord>>() {}.getType();
    }

    public Type getSelResType() {
        return new TypeToken<ArrayList<FakUserRecord>>() {}.getType();
    }

    public Type getUpdResType() {
        return new TypeToken<UpdateResult<FakUserRecord>>() {}.getType();
    }

    public Type getDelResType() {
        return new TypeToken<DeleteResult<FakUserRecord>>() {}.getType();
    }

    public final PGField<FakUserRecord, String> EMAIL = new PGField<>("email");
    public final PGField<FakUserRecord, String> PHONE_NUMBER = new PGField<>("phone_number");
    public final PGField<FakUserRecord, Integer> TM_ID = new PGField<>("tm_id");
    public final PGField<FakUserRecord, Integer> ID = new PGField<>("id");
    public final PGField<FakUserRecord, Boolean> IS_ACTIVE = new PGField<>("is_active");
    public final ObjectRelationship<FakUserRecord, FileRecord> PROFILE_PIC = new ObjectRelationship<>("profile_pic");
    public final ArrayRelationship<FakUserRecord, ReviewRecord> REVIEWS = new ArrayRelationship<>("reviews");

}
