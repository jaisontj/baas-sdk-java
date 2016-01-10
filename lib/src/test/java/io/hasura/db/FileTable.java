package io.hasura.db;

import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.util.ArrayList;

class FileTable implements Table<FileRecord> {

    public static final FileTable FILE_TABLE = new FileTable();

    private String tableName;

    public FileTable() {
        this.tableName = "file";
    }

    public String getTableName() {
        return this.tableName;
    }

    public Type getSingleType() {
        return new TypeToken<FileRecord>(){}.getType();
    }

    public Type getListType() {
        return new TypeToken<ArrayList<FileRecord>>(){}.getType();
    }

    public final PGField<FileRecord, String> SERVER_PATH = new PGField<>("server_path");
    public final PGField<FileRecord, String> FILENAME = new PGField<>("filename");
    public final PGField<FileRecord, Integer> ID = new PGField<>("id");

}
