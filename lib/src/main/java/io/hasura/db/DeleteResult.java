package io.hasura.db;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

class DeleteResult<R> {
    private int affectedRows;

    @SerializedName("returning")
    private ArrayList<R> records;

    public int getCount() {
        return affectedRows;
    }

    public ArrayList<R> getRecords() {
        return records;
    }
}
