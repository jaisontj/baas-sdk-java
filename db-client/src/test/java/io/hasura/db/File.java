package io.hasura.db;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.hasura.db.delete.DeleteResult;
import io.hasura.db.insert.InsertResult;
import io.hasura.db.update.UpdateResult;

class File extends Table<FileRecord> {

    public static final File FILE = new File();

    public File() {
        super("file");
    }

    public Type getInsResType() {
        return new TypeToken<InsertResult<FileRecord>>() {}.getType();
    }

    public Type getSelResType() {
        return new TypeToken<ArrayList<FileRecord>>() {}.getType();
    }

    public Type getUpdResType() {
        return new TypeToken<UpdateResult<FileRecord>>() {}.getType();
    }

    public Type getDelResType() {
        return new TypeToken<DeleteResult<FileRecord>>() {}.getType();
    }

    public final PGField<FileRecord, String> SERVER_PATH = new PGField<>("server_path");
    public final PGField<FileRecord, String> FILENAME = new PGField<>("filename");
    public final PGField<FileRecord, Integer> ID = new PGField<>("id");

}
