package io.hasura.db;

import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.ArrayList;

class FileTable extends Table<FileRecord> {

    public static final FileTable FILE_TABLE = new FileTable();

    public FileTable() {
        super("file");
    }

    public Type getInsResType() {
        return new TypeToken<InsertResult<FileRecord>>() {}.getType();
    }

    public Type getSelResType() {
        return new TypeToken<ArrayList<FileRecord>>() {}.getType();
    }

    public final PGField<FileRecord, String> SERVER_PATH = new PGField<>("server_path");
    public final PGField<FileRecord, String> FILENAME = new PGField<>("filename");
    public final PGField<FileRecord, Integer> ID = new PGField<>("id");

}
