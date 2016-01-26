package io.hasura.db;

import com.google.gson.annotations.SerializedName;

public class InsertResult<R> {
    private int affectedRows;

    @SerializedName("returning")
    private R record;

    public int getCount() {
        return affectedRows;
    }

    public R getRecord() {
        return record;
    }
}
